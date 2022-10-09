package admin.adminsiteserver.qna.application;

import admin.adminsiteserver.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.common.dto.PageInfo;
import admin.adminsiteserver.authentication.ui.AuthenticationPrincipal;
import admin.adminsiteserver.authentication.ui.LoginUserInfo;
import admin.adminsiteserver.qna.application.dto.QnaResponse;
import admin.adminsiteserver.qna.application.dto.QnaSimpleResponse;
import admin.adminsiteserver.qna.domain.*;
import admin.adminsiteserver.qna.domain.answer.Answer;
import admin.adminsiteserver.qna.domain.answer.AnswerComment;
import admin.adminsiteserver.qna.domain.answer.AnswerRepository;
import admin.adminsiteserver.qna.exception.*;
import admin.adminsiteserver.qna.ui.dto.*;
import admin.adminsiteserver.qna.ui.dto.answer.AnswerCommentRequest;
import admin.adminsiteserver.qna.ui.dto.answer.AnswerUpdateRequest;
import admin.adminsiteserver.qna.ui.dto.answer.AnswerUploadRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public QnaResponse uploadQna(UploadQnaRequest request, @AuthenticationPrincipal LoginUserInfo loginUserInfo) {
        Qna qna = request.createQna(loginUserInfo);
        qna.saveFilePaths(request.toQuestionFilePaths());
        qnaRepository.save(qna);
        return QnaResponse.from(qna);
    }

    @Transactional
    public QnaResponse updateQna(UpdateQnaRequest request, LoginUserInfo loginUserInfo, Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(NotExistQnaException::new);
        validateAuthorityForQna(loginUserInfo, qna);

        qna.updateContentAndTitle(request.getTitle(), request.getContent());
        qna.saveFilePaths(request.toQuestionFilePaths());
        qna.deleteFilePaths(request.getDeleteFileUrls());
        s3Uploader.delete(request.getDeleteFileUrls());

        return QnaResponse.from(qna);
    }

    @Transactional
    public void deleteQna(LoginUserInfo loginUserInfo, Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(NotExistQnaException::new);
        validateAuthorityForQna(loginUserInfo, qna);
        s3Uploader.delete(qna.findDeleteFilePaths());
        qnaRepository.delete(qna);
    }

    @Transactional
    public void addQuestionComment(Long questionId, QuestionCommentRequest request, LoginUserInfo loginUserInfo) {
        Qna qna = qnaRepository.findById(questionId)
                .orElseThrow(NotExistQnaException::new);
        qna.addComment(request.toQuestionComment(loginUserInfo));
    }

    @Transactional
    public void updateQuestionComment(Long questionId, Long commentId, QuestionCommentRequest request, LoginUserInfo loginUserInfo) {
        Qna qna = qnaRepository.findById(questionId)
                .orElseThrow(NotExistQnaException::new);
        QuestionComment comment = qna.findUpdateOrDeleteComment(commentId);
        validateAuthorityForQuestionComment(loginUserInfo, comment);
        comment.updateComment(request.getComment());
    }

    @Transactional
    public void deleteQuestionComment(Long questionId, Long commentId, LoginUserInfo loginUserInfo) {
        Qna qna = qnaRepository.findById(questionId)
                .orElseThrow(NotExistQnaException::new);
        QuestionComment comment = qna.findUpdateOrDeleteComment(commentId);
        validateAuthorityForQuestionComment(loginUserInfo, comment);
        qna.deleteComment(comment);
    }

    @Transactional
    public void uploadAnswer(LoginUserInfo loginUserInfo, Long qnaId, AnswerUploadRequest request) {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(NotExistQnaException::new);
        Answer answer = request.createAnswer(loginUserInfo);
        answer.saveFilePaths(request.toAnswerFilePaths());
        qna.addAnswer(answer);
    }

    @Transactional
    public void updateAnswer(AnswerUpdateRequest request, LoginUserInfo loginUserInfo, Long qnaId, Long answerId) {
        Answer answer = findAnswer(qnaId, answerId);
        validateAuthorityForAnswer(loginUserInfo, answer);

        answer.updateContent(request.getContent());
        answer.saveFilePaths(request.toAnswerFilePaths());
        answer.deleteFilePaths(request.getDeleteFileUrls());
        s3Uploader.delete(request.getDeleteFileUrls());
    }

    @Transactional
    public void deleteAnswer(LoginUserInfo loginUserInfo, Long qnaId, Long answerId) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(NotExistQnaException::new);
        Answer answer = qna.findAnswer(answerId);
        validateAuthorityForAnswer(loginUserInfo, answer);
        s3Uploader.delete(answer.findDeleteFilePaths());
        qna.deleteAnswer(answer);
    }

    @Transactional
    public void addAnswerComment(Long questionId, Long answerId, AnswerCommentRequest request, LoginUserInfo loginUserInfo) {
        Answer answer = findAnswer(questionId, answerId);
        answer.addComment(request.toAnswerComment(loginUserInfo));
    }

    @Transactional
    public void updateAnswerComment(Long questionId, Long answerId, Long commentId, AnswerCommentRequest request, LoginUserInfo loginUserInfo) {
        Answer answer = findAnswer(questionId, answerId);
        AnswerComment comment = answer.findUpdateOrDeleteComment(commentId);
        validateAuthorityForAnswerComment(loginUserInfo, comment);
        comment.updateComment(request.getComment());
    }

    @Transactional
    public void deleteAnswerComment(Long questionId, Long answerId, Long commentId, LoginUserInfo loginUserInfo) {
        Answer answer = findAnswer(questionId, answerId);
        AnswerComment comment = answer.findUpdateOrDeleteComment(commentId);
        validateAuthorityForAnswerComment(loginUserInfo, comment);
        answer.deleteComment(comment);
    }

    private Answer findAnswer(Long questionId, Long answerId) {
        Qna qna = qnaRepository.findById(questionId)
                .orElseThrow(NotExistQnaException::new);
        return qna.findAnswer(answerId);
    }

    public QnaResponse findOne(Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(NotExistQnaException::new);
        return QnaResponse.from(qna);
    }

    public CommonResponse<List<QnaSimpleResponse>> findQnas(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());
        Page<QnaSimpleResponse> qnas = qnaRepository.findAll(pageRequest).map(QnaSimpleResponse::from);
        return CommonResponse.of(qnas.getContent(), PageInfo.from(qnas), INQUIRE_QNA_LIST_SUCCESS.getMessage());
    }

    private void validateAuthorityForAnswer(LoginUserInfo loginUserInfo, Answer findAnswer) {
        if (loginUserInfo.isNotEqualUser(findAnswer.getAuthorEmail())) {
            throw new UnauthorizedForAnswerException();
        }
    }

    private void validateAuthorityForQuestionComment(LoginUserInfo loginUserInfo, QuestionComment comment) {
        if (loginUserInfo.isNotEqualUser(comment.getAuthorEmail())) {
            throw new UnauthorizedForQuestionCommentException();
        }
    }

    private void validateAuthorityForQna(LoginUserInfo loginUserInfo, Qna qna) {
        if (loginUserInfo.isNotEqualUser(qna.getAuthorEmail())) {
            throw new UnauthorizedForQnaException();
        }
    }

    private void validateAuthorityForAnswerComment(LoginUserInfo loginUserInfo, AnswerComment comment) {
        if (loginUserInfo.isNotEqualUser(comment.getAuthorEmail())) {
            throw new UnauthorizedForAnswerCommentException();
        }
    }
}
