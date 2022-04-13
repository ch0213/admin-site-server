package admin.adminsiteserver.qna.application;

import admin.adminsiteserver.announcement.domain.AnnouncementFilePath;
import admin.adminsiteserver.common.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.common.dto.PageInfo;
import admin.adminsiteserver.member.auth.util.LoginUser;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.qna.application.dto.AnswerCommentResponse;
import admin.adminsiteserver.qna.application.dto.AnswerDto;
import admin.adminsiteserver.qna.application.dto.QnaResponse;
import admin.adminsiteserver.qna.application.dto.QuestionCommentResponse;
import admin.adminsiteserver.qna.domain.*;
import admin.adminsiteserver.qna.exception.*;
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
    public QnaResponse uploadQna(UploadQnaRequest request, @LoginUser LoginUserInfo loginUserInfo) {
        Qna qna = request.createQna(loginUserInfo);
        qnaRepository.save(qna);
        return QnaResponse.from(qna);
    }

    @Transactional
    public QnaResponse updateQna(UpdateQnaRequest request, LoginUserInfo loginUserInfo, Long qnaId) {
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
    public void deleteQna(LoginUserInfo loginUserInfo, Long qnaId) {
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
    public QuestionCommentResponse addQuestionComment(Long questionId, QuestionCommentRequest request, LoginUserInfo loginUserInfo) {
        Qna qna = qnaRepository.findById(questionId)
                .orElseThrow(NotExistQnaException::new);
        QuestionComment comment = request.toQuestionComment(loginUserInfo);
        qna.addComment(comment);
        return QuestionCommentResponse.from(comment);
    }

    @Transactional
    public QuestionCommentResponse updateQuestionComment(Long questionId, Long commentId, QuestionCommentRequest request, LoginUserInfo loginUserInfo) {
        Qna qna = qnaRepository.findById(questionId)
                .orElseThrow(NotExistQnaException::new);
        QuestionComment updateComment = qna.getComments().stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findAny()
                .orElseThrow(NotExistQuestionCommentException::new);
        validateAuthorityForQuestionComment(loginUserInfo, updateComment);
        updateComment.updateComment(request.getComment());
        return QuestionCommentResponse.from(updateComment);
    }

    @Transactional
    public void deleteQuestionComment(Long questionId, Long commentId, LoginUserInfo loginUserInfo) {
        Qna qna = qnaRepository.findById(questionId)
                .orElseThrow(NotExistQnaException::new);
        QuestionComment deleteComment = qna.getComments().stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findAny()
                .orElseThrow(NotExistQuestionCommentException::new);
        validateAuthorityForQuestionComment(loginUserInfo, deleteComment);
        qna.getComments().remove(deleteComment);
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
        Answer findAnswer = findAnswer(qnaId, answerId);
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
        Answer findAnswer = findAnswer(qnaId, answerId);
        validateAuthorityForAnswer(loginUserInfo, findAnswer);

        List<FilePathDto> deleteFileURls = findAnswer.getFiles().stream()
                .map(answerFilePath -> FilePathDto.from(AnswerFilePath.class))
                .collect(Collectors.toList());
        s3Uploader.delete(deleteFileURls);
        answerRepository.delete(findAnswer);
    }

    @Transactional
    public AnswerCommentResponse addAnswerComment(Long questionId, Long answerId, AnswerCommentRequest request, LoginUserInfo loginUserInfo) {
        Answer addCommentAnswer = findAnswer(questionId, answerId);
        AnswerComment comment = request.toAnswerComment(loginUserInfo);
        addCommentAnswer.getComments().add(comment);
        return AnswerCommentResponse.from(comment);
    }

    @Transactional
    public AnswerCommentResponse updateAnswerComment(Long questionId, Long answerId, Long commentId, AnswerCommentRequest request, LoginUserInfo loginUserInfo) {
        Answer updateAnswer = findAnswer(questionId, answerId);
        AnswerComment updateComment = updateAnswer.getComments().stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findAny()
                .orElseThrow(NotExistAnswerCommentException::new);
        validateAuthorityForAnswerComment(loginUserInfo, updateComment);
        updateComment.updateComment(request.getComment());
        return AnswerCommentResponse.from(updateComment);
    }

    @Transactional
    public void deleteAnswerComment(Long questionId, Long answerId, Long commentId, LoginUserInfo loginUserInfo) {
        Qna qna = qnaRepository.findById(questionId)
                .orElseThrow(NotExistQnaException::new);
        Answer deleteCommentAnswer = qna.getAnswers().stream()
                .filter(answer -> answer.getId().equals(answerId))
                .findAny()
                .orElseThrow(NotExistAnswerException::new);
        AnswerComment deleteComment = deleteCommentAnswer.getComments().stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findAny()
                .orElseThrow(NotExistAnswerCommentException::new);
        validateAuthorityForAnswerComment(loginUserInfo, deleteComment);
        deleteCommentAnswer.getComments().remove(deleteComment);
    }

    private Answer findAnswer(Long questionId, Long answerId) {
        Qna qna = qnaRepository.findById(questionId)
                .orElseThrow(NotExistQnaException::new);
        return qna.getAnswers().stream()
                .filter(answer -> answer.getId().equals(answerId))
                .findAny()
                .orElseThrow(NotExistAnswerException::new);
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

    private void validateAuthorityForQuestionComment(LoginUserInfo loginUserInfo, QuestionComment comment) {
        if (!loginUserInfo.getUserId().equals(comment.getAuthorId())) {
            throw new UnauthorizedForQuestionCommentException();
        }
    }

    private void validateAuthorityForQna(LoginUserInfo loginUserInfo, Qna qna) {
        if (!loginUserInfo.getUserId().equals(qna.getAuthorId())) {
            throw new UnauthorizedForQnaException();
        }
    }

    private void validateAuthorityForAnswerComment(LoginUserInfo loginUserInfo, AnswerComment comment) {
        if (!loginUserInfo.getUserId().equals(comment.getAuthorId())) {
            throw new UnauthorizedForAnswerCommentException();
        }
    }
}
