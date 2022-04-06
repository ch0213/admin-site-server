package admin.adminsiteserver.qna.ui.dto;

import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.qna.domain.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class AnswerUploadRequest {
    private String content;
    private List<MultipartFile> images;

    public Answer createAnswer(LoginUserInfo loginUserInfo) {
        return new Answer(loginUserInfo.getUserId(), loginUserInfo.getName(), content);
    }
}
