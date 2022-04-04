package admin.adminsiteserver.qna.ui;

import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.member.auth.util.LoginUser;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.qna.application.QnaService;
import admin.adminsiteserver.qna.application.dto.QnaResponse;
import admin.adminsiteserver.qna.ui.dto.UpdateQnaRequest;
import admin.adminsiteserver.qna.ui.dto.UploadQnaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static admin.adminsiteserver.qna.ui.QnaResponseMessage.QNA_UPDATE_SUCCESS;
import static admin.adminsiteserver.qna.ui.QnaResponseMessage.QNA_UPLOAD_SUCCESS;

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
    public CommonResponse<Void> updateQna(
            UpdateQnaRequest request,
            @LoginUser LoginUserInfo loginUserInfo,
            @PathVariable Long qnaId
    ) {
        qnaService.update(request, loginUserInfo, qnaId);
        return CommonResponse.from(QNA_UPDATE_SUCCESS.getMessage());
    }
}
