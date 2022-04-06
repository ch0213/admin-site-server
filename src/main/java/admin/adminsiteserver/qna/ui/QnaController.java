package admin.adminsiteserver.qna.ui;

import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.member.auth.util.LoginUser;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.qna.application.QnaService;
import admin.adminsiteserver.qna.application.dto.AnswerDto;
import admin.adminsiteserver.qna.application.dto.QnaResponse;
import admin.adminsiteserver.qna.ui.dto.AnswerRequest;
import admin.adminsiteserver.qna.ui.dto.AnswerUpdateRequest;
import admin.adminsiteserver.qna.ui.dto.UpdateQnaRequest;
import admin.adminsiteserver.qna.ui.dto.UploadQnaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        qnaService.delete(qnaId);
        return CommonResponse.from(QNA_DELETE_SUCCESS.getMessage());
    }

    @PostMapping("/{qnaId}/answer")
    public CommonResponse<AnswerDto> uploadAnswer(AnswerRequest request, @LoginUser LoginUserInfo loginUserInfo, @PathVariable Long qnaId) {
        AnswerDto answerDto = qnaService.uploadAnswer(loginUserInfo, qnaId, request);
        return CommonResponse.of(answerDto, ANSWER_UPLOAD_SUCCESS.getMessage());
    }

    @PutMapping("/{qnaId}/answer/{answerId}")
    public CommonResponse<AnswerDto> updateAnswer(AnswerUpdateRequest request, @PathVariable Long qnaId, @PathVariable Long answerId) {
        AnswerDto answerDto = qnaService.updateAnswer(request, qnaId, answerId);
        return CommonResponse.of(answerDto, ANSWER_UPDATE_SUCCESS.getMessage());
    }
}
