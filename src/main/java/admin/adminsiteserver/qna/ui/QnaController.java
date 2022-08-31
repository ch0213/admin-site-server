package admin.adminsiteserver.qna.ui;

import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.authentication.util.AuthenticationPrincipal;
import admin.adminsiteserver.authentication.ui.LoginUserInfo;
import admin.adminsiteserver.qna.application.QnaService;
import admin.adminsiteserver.qna.application.dto.*;
import admin.adminsiteserver.qna.application.dto.answer.AnswerCommentResponse;
import admin.adminsiteserver.qna.ui.dto.*;
import admin.adminsiteserver.qna.ui.dto.answer.AnswerCommentRequest;
import admin.adminsiteserver.qna.ui.dto.answer.AnswerUpdateRequest;
import admin.adminsiteserver.qna.ui.dto.answer.AnswerUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static admin.adminsiteserver.qna.ui.QnaResponseMessage.*;

@RestController
@RequestMapping("/qnas")
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;

    @PostMapping
    public CommonResponse<QnaResponse> uploadQna(@Valid @RequestBody UploadQnaRequest request, @AuthenticationPrincipal LoginUserInfo loginUserInfo) {
        QnaResponse response = qnaService.uploadQna(request, loginUserInfo);
        return CommonResponse.of(response, QNA_UPLOAD_SUCCESS.getMessage());
    }

    @PutMapping("/{qnaId}")
    public CommonResponse<QnaResponse> updateQna(
            @Valid @RequestBody UpdateQnaRequest request,
            @AuthenticationPrincipal LoginUserInfo loginUserInfo,
            @PathVariable Long qnaId
    ) {
        QnaResponse qnaResponse = qnaService.updateQna(request, loginUserInfo, qnaId);
        return CommonResponse.of(qnaResponse, QNA_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{qnaId}")
    public CommonResponse<Void> deleteQna(@AuthenticationPrincipal LoginUserInfo loginUserInfo, @PathVariable Long qnaId) {
        qnaService.deleteQna(loginUserInfo, qnaId);
        return CommonResponse.from(QNA_DELETE_SUCCESS.getMessage());
    }

    @PostMapping("/{qnaId}/answers")
    public CommonResponse<Void> uploadAnswer(@Valid @RequestBody AnswerUploadRequest request, @AuthenticationPrincipal LoginUserInfo loginUserInfo, @PathVariable Long qnaId) {
        qnaService.uploadAnswer(loginUserInfo, qnaId, request);
        return CommonResponse.from(ANSWER_UPLOAD_SUCCESS.getMessage());
    }

    @PutMapping("/{qnaId}/answers/{answerId}")
    public CommonResponse<Void> updateAnswer(
            @Valid @RequestBody AnswerUpdateRequest request,
            @AuthenticationPrincipal LoginUserInfo loginUserInfo,
            @PathVariable Long qnaId,
            @PathVariable Long answerId
    ) {
        qnaService.updateAnswer(request, loginUserInfo, qnaId, answerId);
        return CommonResponse.from(ANSWER_UPDATE_SUCCESS.getMessage());
    }
    
    @DeleteMapping("/{qnaId}/answers/{answerId}")
    public CommonResponse<Void> deleteAnswer(
            @AuthenticationPrincipal LoginUserInfo loginUserInfo,
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
    public CommonResponse<List<QnaSimpleResponse>> findQnaList(Pageable pageable) {
        return qnaService.findQnas(pageable);
    }

    @PostMapping("/{qnaId}/comments")
    public CommonResponse<QuestionCommentResponse> addQuestionComment(
            @PathVariable Long qnaId,
            @Valid @RequestBody QuestionCommentRequest request,
            @AuthenticationPrincipal LoginUserInfo loginUserInfo
    ) {
        qnaService.addQuestionComment(qnaId, request, loginUserInfo);
        return CommonResponse.from(QNA_COMMENT_UPLOAD_SUCCESS.getMessage());
    }

    @PutMapping("/{qnaId}/comments/{commentId}")
    public CommonResponse<QuestionCommentResponse> updateQuestionComment(
            @PathVariable Long qnaId,
            @PathVariable Long commentId,
            @Valid @RequestBody QuestionCommentRequest request,
            @AuthenticationPrincipal LoginUserInfo loginUserInfo
    ) {
        qnaService.updateQuestionComment(qnaId, commentId, request, loginUserInfo);
        return CommonResponse.from(QNA_COMMENT_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{qnaId}/comments/{commentId}")
    public CommonResponse<Void> deleteQuestionComment(
            @PathVariable Long qnaId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal LoginUserInfo loginUserInfo
    ) {
        qnaService.deleteQuestionComment(qnaId, commentId, loginUserInfo);
        return CommonResponse.from(QNA_COMMENT_DELETE_SUCCESS.getMessage());
    }

    @PostMapping("/{qnaId}/answers/{answerId}/comments")
    public CommonResponse<AnswerCommentResponse> addAnswerComment(
            @PathVariable Long qnaId,
            @PathVariable Long answerId,
            @Valid @RequestBody AnswerCommentRequest request,
            @AuthenticationPrincipal LoginUserInfo loginUserInfo
    ) {
        qnaService.addAnswerComment(qnaId, answerId, request, loginUserInfo);
        return CommonResponse.from(QNA_COMMENT_UPLOAD_SUCCESS.getMessage());
    }

    @PutMapping("/{qnaId}/answers/{answerId}/comments/{commentId}")
    public CommonResponse<AnswerCommentResponse> updateAnswerComment(
            @PathVariable Long qnaId,
            @PathVariable Long answerId,
            @PathVariable Long commentId,
            @Valid @RequestBody AnswerCommentRequest request,
            @AuthenticationPrincipal LoginUserInfo loginUserInfo
    ) {
        qnaService.updateAnswerComment(qnaId, answerId, commentId, request, loginUserInfo);
        return CommonResponse.from(QNA_COMMENT_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{qnaId}/answers/{answerId}/comments/{commentId}")
    public CommonResponse<Void> deleteAnswerComment(
            @PathVariable Long qnaId,
            @PathVariable Long answerId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal LoginUserInfo loginUserInfo
    ) {
        qnaService.deleteAnswerComment(qnaId, answerId, commentId, loginUserInfo);
        return CommonResponse.from(QNA_COMMENT_DELETE_SUCCESS.getMessage());
    }
}
