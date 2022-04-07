package admin.adminsiteserver.qna.ui;

import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.member.auth.util.LoginUser;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.qna.application.QnaService;
import admin.adminsiteserver.qna.application.dto.AnswerDto;
import admin.adminsiteserver.qna.application.dto.QnaResponse;
import admin.adminsiteserver.qna.ui.dto.AnswerUploadRequest;
import admin.adminsiteserver.qna.ui.dto.AnswerUpdateRequest;
import admin.adminsiteserver.qna.ui.dto.UpdateQnaRequest;
import admin.adminsiteserver.qna.ui.dto.UploadQnaRequest;
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
    public CommonResponse<QnaResponse> uploadQna(UploadQnaRequest request, @LoginUser LoginUserInfo loginUserInfo) {
        QnaResponse response = qnaService.upload(request, loginUserInfo);
        return CommonResponse.of(response, QNA_UPLOAD_SUCCESS.getMessage());
    }

    @PutMapping("/{qnaId}")
    public CommonResponse<QnaResponse> updateQna(
            UpdateQnaRequest request,
            @LoginUser LoginUserInfo loginUserInfo,
            @PathVariable Long qnaId
    ) {
        QnaResponse qnaResponse = qnaService.update(request, loginUserInfo, qnaId);
        return CommonResponse.of(qnaResponse, QNA_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{qnaId}")
    public CommonResponse<Void> deleteQna(@LoginUser LoginUserInfo loginUserInfo, @PathVariable Long qnaId) {
        qnaService.delete(loginUserInfo, qnaId);
        return CommonResponse.from(QNA_DELETE_SUCCESS.getMessage());
    }

    @PostMapping("/{qnaId}/answer")
    public CommonResponse<AnswerDto> uploadAnswer(AnswerUploadRequest request, @LoginUser LoginUserInfo loginUserInfo, @PathVariable Long qnaId) {
        AnswerDto answerDto = qnaService.uploadAnswer(loginUserInfo, qnaId, request);
        return CommonResponse.of(answerDto, ANSWER_UPLOAD_SUCCESS.getMessage());
    }

    @PutMapping("/{qnaId}/answer/{answerId}")
    public CommonResponse<AnswerDto> updateAnswer(
            AnswerUpdateRequest request,
            @LoginUser LoginUserInfo loginUserInfo,
            @PathVariable Long qnaId,
            @PathVariable Long answerId
    ) {
        AnswerDto answerDto = qnaService.updateAnswer(request, loginUserInfo, qnaId, answerId);
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
}
