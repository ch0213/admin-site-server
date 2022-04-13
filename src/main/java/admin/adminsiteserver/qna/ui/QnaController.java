package admin.adminsiteserver.qna.ui;

import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.member.auth.util.LoginUser;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.qna.application.QnaService;
import admin.adminsiteserver.qna.application.dto.AnswerCommentResponse;
import admin.adminsiteserver.qna.application.dto.AnswerResponse;
import admin.adminsiteserver.qna.application.dto.QnaResponse;
import admin.adminsiteserver.qna.application.dto.QuestionCommentResponse;
import admin.adminsiteserver.qna.ui.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static admin.adminsiteserver.qna.ui.QnaResponseMessage.*;

@RestController
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;

    @PostMapping
    public CommonResponse<QnaResponse> uploadQna(@RequestBody UploadQnaRequest request, @LoginUser LoginUserInfo loginUserInfo) {
        QnaResponse response = qnaService.uploadQna(request, loginUserInfo);
        return CommonResponse.of(response, QNA_UPLOAD_SUCCESS.getMessage());
    }

    @PutMapping("/{qnaId}")
    public CommonResponse<QnaResponse> updateQna(
            UpdateQnaRequest request,
            @LoginUser LoginUserInfo loginUserInfo,
            @PathVariable Long qnaId
    ) {
        QnaResponse qnaResponse = qnaService.updateQna(request, loginUserInfo, qnaId);
        return CommonResponse.of(qnaResponse, QNA_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{qnaId}")
    public CommonResponse<Void> deleteQna(@LoginUser LoginUserInfo loginUserInfo, @PathVariable Long qnaId) {
        qnaService.deleteQna(loginUserInfo, qnaId);
        return CommonResponse.from(QNA_DELETE_SUCCESS.getMessage());
    }

    @PostMapping("/{qnaId}/answer")
    public CommonResponse<AnswerResponse> uploadAnswer(@RequestBody AnswerUploadRequest request, @LoginUser LoginUserInfo loginUserInfo, @PathVariable Long qnaId) {
        AnswerResponse answerDto = qnaService.uploadAnswer(loginUserInfo, qnaId, request);
        return CommonResponse.of(answerDto, ANSWER_UPLOAD_SUCCESS.getMessage());
    }

    @PutMapping("/{qnaId}/answer/{answerId}")
    public CommonResponse<AnswerResponse> updateAnswer(
            @RequestBody AnswerUpdateRequest request,
            @LoginUser LoginUserInfo loginUserInfo,
            @PathVariable Long qnaId,
            @PathVariable Long answerId
    ) {
        AnswerResponse answerDto = qnaService.updateAnswer(request, loginUserInfo, qnaId, answerId);
        return CommonResponse.of(answerDto, ANSWER_UPDATE_SUCCESS.getMessage());
    }
    
    @DeleteMapping("/{qnaId}/answer/{answerId}")
    public CommonResponse<Void> deleteAnswer(
            @LoginUser LoginUserInfo loginUserInfo,
            @PathVariable Long qnaId,
            @PathVariable Long answerId
    ) {
        qnaService.deleteAnswer(loginUserInfo, qnaId, answerId);
        return CommonResponse.from(ANSWER_DELETE_SUCCESS.getMessage());
    }

    @GetMapping("/{qnaId}")
    public CommonResponse<QnaResponse> findOne(@PathVariable Long qnaId) {
        return CommonResponse.of(qnaService.findOne(qnaId), INQUIRE_QNA_SUCCESS.getMessage());
    }

    @GetMapping
    public CommonResponse<List<QnaResponse>> findQnaList(Pageable pageable) {
        return qnaService.findQnas(pageable);
    }

    @PostMapping("/{qnaId}/comment")
    public CommonResponse<QuestionCommentResponse> addQuestionComment(
            @PathVariable Long qnaId,
            @RequestBody QuestionCommentRequest request,
            @LoginUser LoginUserInfo loginUserInfo
    ) {
        QuestionCommentResponse response = qnaService.addQuestionComment(qnaId, request, loginUserInfo);
        return CommonResponse.of(response, QNA_COMMENT_UPLOAD_SUCCESS.getMessage());
    }

    @PutMapping("/{qnaId}/comment/{commentId}")
    public CommonResponse<QuestionCommentResponse> updateQuestionComment(
            @PathVariable Long qnaId,
            @PathVariable Long commentId,
            @RequestBody QuestionCommentRequest request,
            @LoginUser LoginUserInfo loginUserInfo
    ) {
        QuestionCommentResponse response = qnaService.updateQuestionComment(qnaId, commentId, request, loginUserInfo);
        return CommonResponse.of(response, QNA_COMMENT_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{qnaId}/comment/{commentId}")
    public CommonResponse<Void> deleteQuestionComment(
            @PathVariable Long qnaId,
            @PathVariable Long commentId,
            @LoginUser LoginUserInfo loginUserInfo
    ) {
        qnaService.deleteQuestionComment(qnaId, commentId, loginUserInfo);
        return CommonResponse.from(QNA_COMMENT_DELETE_SUCCESS.getMessage());
    }

    @PostMapping("/{qnaId}/answer/{answerId}/comment")
    public CommonResponse<AnswerCommentResponse> addAnswerComment(
            @PathVariable Long qnaId,
            @PathVariable Long answerId,
            @RequestBody AnswerCommentRequest request,
            @LoginUser LoginUserInfo loginUserInfo
    ) {
        AnswerCommentResponse response = qnaService.addAnswerComment(qnaId, answerId, request, loginUserInfo);
        return CommonResponse.of(response, QNA_COMMENT_UPLOAD_SUCCESS.getMessage());
    }

    @PutMapping("/{qnaId}/answer/{answerId}/comment/{commentId}")
    public CommonResponse<AnswerCommentResponse> updateAnswerComment(
            @PathVariable Long qnaId,
            @PathVariable Long answerId,
            @PathVariable Long commentId,
            @RequestBody AnswerCommentRequest request,
            @LoginUser LoginUserInfo loginUserInfo
    ) {
        AnswerCommentResponse response = qnaService.updateAnswerComment(qnaId, answerId, commentId, request, loginUserInfo);
        return CommonResponse.of(response, QNA_COMMENT_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{qnaId}/answer/{answerId}/comment/{commentId}")
    public CommonResponse<Void> deleteAnswerComment(
            @PathVariable Long qnaId,
            @PathVariable Long answerId,
            @PathVariable Long commentId,
            @LoginUser LoginUserInfo loginUserInfo
    ) {
        qnaService.deleteAnswerComment(qnaId, answerId, commentId, loginUserInfo);
        return CommonResponse.from(QNA_COMMENT_DELETE_SUCCESS.getMessage());
    }
}
