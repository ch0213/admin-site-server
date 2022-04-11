package admin.adminsiteserver.qna.application;

import admin.adminsiteserver.announcement.domain.AnnouncementFilePath;
import admin.adminsiteserver.common.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.common.dto.PageInfo;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static admin.adminsiteserver.qna.ui.QnaResponseMessage.INQUIRE_QNA_LIST_SUCCESS;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QnaService {
    private final QnaRepository qnaRepository;
    private final AnswerRepository answerRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public QnaResponse upload(UploadQnaRequest request, @LoginUser LoginUserInfo loginUserInfo) {
        Qna qna = request.createQna(loginUserInfo);
        qnaRepository.save(qna);
        return QnaResponse.from(qna);
    }

    @Transactional
    public QnaResponse update(UpdateQnaRequest request, LoginUserInfo loginUserInfo, Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(NotExistQnaException::new);
        validateAuthorityForQna(loginUserInfo, qna);
        qna.updateContentAndTitle(request.getTitle(), request.getContent());

        qna.getFiles().addAll(request.getFiles().stream()
                .map(filePathDto -> filePathDto.toFilePath(QuestionFilePath.class))
                .collect(Collectors.toList()));

        qna.deleteFiles(request.getDeleteFileUrls());
        s3Uploader.delete(request.getDeleteFileUrls());

        return QnaResponse.from(qna);
    }

    @Transactional
    public void delete(LoginUserInfo loginUserInfo, Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(NotExistQnaException::new);
        validateAuthorityForQna(loginUserInfo, qna);
        List<FilePathDto> deleteFileUrls = qna.getFiles().stream()
                .map(questionFilePath -> FilePathDto.from(QuestionFilePath.class))
                .collect(Collectors.toList());

//        qna.getAnswers().stream().map() answer도 file 삭제
        s3Uploader.delete(deleteFileUrls);
        qnaRepository.delete(qna);
    }

    @Transactional
    public AnswerDto uploadAnswer(LoginUserInfo loginUserInfo, Long qnaId, AnswerUploadRequest request) {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(NotExistQnaException::new);
        Answer answer = request.createAnswer(loginUserInfo);
        answerRepository.save(answer);
        qna.addAnswer(answer);
        return AnswerDto.from(answer);
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

        findAnswer.getFiles().addAll(request.getFiles().stream()
                .map(filePathDto -> filePathDto.toFilePath(AnswerFilePath.class))
                .collect(Collectors.toList()));

        findAnswer.deleteFiles(request.getDeleteFileUrls());
        s3Uploader.delete(request.getDeleteFileUrls());

        return AnswerDto.from(findAnswer);
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

        List<FilePathDto> deleteFileURls = findAnswer.getFiles().stream()
                .map(announcementFilePath -> FilePathDto.from(AnnouncementFilePath.class))
                .collect(Collectors.toList());
        s3Uploader.delete(deleteFileURls);
        answerRepository.delete(findAnswer);
    }

    public QnaResponse findOne(Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(NotExistQnaException::new);
        return QnaResponse.from(qna);
    }

    public CommonResponse<List<QnaResponse>> findQnas(Pageable pageable) {
        Page<QnaResponse> qnas = qnaRepository.findAll(pageable).map(QnaResponse::toInstanceOfList);
        return CommonResponse.of(qnas.getContent(), PageInfo.from(qnas), INQUIRE_QNA_LIST_SUCCESS.getMessage());
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
}
