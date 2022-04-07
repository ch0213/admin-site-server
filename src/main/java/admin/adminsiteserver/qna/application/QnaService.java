package admin.adminsiteserver.qna.application;

import admin.adminsiteserver.common.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.member.auth.util.LoginUser;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.qna.application.dto.AnswerDto;
import admin.adminsiteserver.qna.application.dto.QnaResponse;
import admin.adminsiteserver.qna.domain.*;
import admin.adminsiteserver.qna.exception.NotExistAnswerException;
import admin.adminsiteserver.qna.exception.NotExistQnaException;
import admin.adminsiteserver.qna.exception.UnauthorizedForAnswerException;
import admin.adminsiteserver.qna.exception.UnauthorizedForQnaException;
import admin.adminsiteserver.qna.ui.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QnaService {
    private final QnaRepository qnaRepository;
    private final AnswerRepository answerRepository;
    private final S3Uploader s3Uploader;
    private static final String QUESTION_IMAGE_PATH = "qna/question";
    private static final String ANSWER_IMAGE_PATH = "qna/answer";

    @Transactional
    public QnaResponse upload(UploadQnaRequest request, @LoginUser LoginUserInfo loginUserInfo) {
        Qna qna = request.createQna(loginUserInfo);
        List<FilePathDto> filePathDtos = new ArrayList<>();
        if (request.getImages() != null) {
            filePathDtos = saveQuestionImages(request);
            List<QuestionFilePath> imagePaths = getImagePathsFromDto(filePathDtos, QuestionFilePath.class);
            qna.addQuestionImages(imagePaths);
        }
        qnaRepository.save(qna);
        return QnaResponse.of(qna, filePathDtos);
    }

    @Transactional
    public QnaResponse update(UpdateQnaRequest request, LoginUserInfo loginUserInfo, Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(NotExistQnaException::new);
        qna.updateContentAndTitle(request.getTitle(), request.getContent());
        validateAuthorityForQna(loginUserInfo, qna);
        if (request.getImages() != null) {
            List<FilePathDto> filePathDtos = saveQuestionImages(request);
            qna.addQuestionImages(getImagePathsFromDto(filePathDtos, QuestionFilePath.class));
        }

        if (request.getDeleteFileUrls() != null) {
            qna.deleteImages(request.getDeleteFileUrls());
            s3Uploader.delete(request.getDeleteFileUrls());
        }

        return QnaResponse.of(qna, getImagePathDtosFromQna(qna));
    }

    @Transactional
    public void delete(LoginUserInfo loginUserInfo, Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(NotExistQnaException::new);
        validateAuthorityForQna(loginUserInfo, qna);
        List<String> deleteFileUrls = qna.getImages().stream()
                .map(QuestionFilePath::getFileUrl)
                .collect(Collectors.toList());
        s3Uploader.delete(deleteFileUrls);

        qnaRepository.delete(qna);
    }

    @Transactional
    public AnswerDto uploadAnswer(LoginUserInfo loginUserInfo, Long qnaId, AnswerUploadRequest request) {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(NotExistQnaException::new);
        List<FilePathDto> filePathDtos = s3Uploader.upload(request.getImages(), ANSWER_IMAGE_PATH);
        List<AnswerFilePath> answerFilePaths = getImagePathsFromDto(filePathDtos, AnswerFilePath.class);

        Answer answer = request.createAnswer(loginUserInfo);
        answerRepository.save(answer);

        qna.addAnswer(answer);
        answer.addImages(answerFilePaths);

        return AnswerDto.of(answer, filePathDtos);
    }

    @Transactional
    public AnswerDto updateAnswer(AnswerUpdateRequest request, LoginUserInfo loginUserInfo, Long qnaId, Long answerId) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(NotExistQnaException::new);
        Answer findAnswer = qna.getAnswers().stream()
                .filter(answer -> answer.getId().equals(answerId))
                .findAny()
                .orElseThrow(NotExistAnswerException::new);
        validateAuthorityForAnswer(loginUserInfo, findAnswer);
        findAnswer.updateContent(request.getContent());

        if (request.getImages() != null) {
            List<FilePathDto> filePathDtos = saveAnswerImages(request);
            findAnswer.addImages(getImagePathsFromDto(filePathDtos, AnswerFilePath.class));
        }

        if (request.getDeleteFileUrls() != null) {
            findAnswer.deleteImages(request.getDeleteFileUrls());
            s3Uploader.delete(request.getDeleteFileUrls());
        }

        return AnswerDto.of(findAnswer, getImagePathDtosFromAnswer(findAnswer));
    }

    @Transactional
    public void deleteAnswer(LoginUserInfo loginUserInfo, Long qnaId, Long answerId) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(NotExistQnaException::new);
        Answer findAnswer = qna.getAnswers().stream()
                .filter(answer -> answer.getId().equals(answerId))
                .findAny()
                .orElseThrow(NotExistAnswerException::new);
        validateAuthorityForAnswer(loginUserInfo, findAnswer);
        answerRepository.delete(findAnswer);
    }

    private void validateAuthorityForAnswer(LoginUserInfo loginUserInfo, Answer findAnswer) {
        if (!loginUserInfo.getUserId().equals(findAnswer.getAuthorId())) {
            throw new UnauthorizedForAnswerException();
        }
    }

    private void validateAuthorityForQna(LoginUserInfo loginUserInfo, Qna qna) {
        if (!loginUserInfo.getUserId().equals(qna.getAuthorId())) {
            throw new UnauthorizedForQnaException();
        }
    }

    private List<FilePathDto> saveQuestionImages(BaseQnaRequest request) {
        if (request.getImages() == null) {
            return null;
        }
        return s3Uploader.upload(request.getImages(), QUESTION_IMAGE_PATH);
    }

    private List<FilePathDto> saveAnswerImages(AnswerUpdateRequest request) {
        if (request.getImages() == null) {
            return null;
        }
        return s3Uploader.upload(request.getImages(), ANSWER_IMAGE_PATH);
    }

    private List<FilePathDto> getImagePathDtosFromAnswer(Answer answer) {
        return answer.getImages().stream()
                .map(FilePathDto::from)
                .collect(Collectors.toList());
    }

    private List<FilePathDto> getImagePathDtosFromQna(Qna qna) {
        return qna.getImages().stream()
                .map(FilePathDto::from)
                .collect(Collectors.toList());
    }

    private <T> List<T> getImagePathsFromDto(List<FilePathDto> imagePathDtos, Class<T> clazz) {
        return imagePathDtos.stream()
                .map(filePathDto -> filePathDto.toFilePath(clazz))
                .collect(Collectors.toList());
    }
}
