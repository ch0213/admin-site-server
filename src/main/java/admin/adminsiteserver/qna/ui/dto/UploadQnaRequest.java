package admin.adminsiteserver.qna.ui.dto;

import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.qna.domain.Qna;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class UploadQnaRequest {
    private String title;
    private String content;
    private List<MultipartFile> images;

    public Qna createQna(LoginUserInfo loginUserInfo) {
        return Qna.builder()
                .authorId(loginUserInfo.getUserId())
                .authorName(loginUserInfo.getName())
                .title(title)
                .content(content)
                .build();
    }
}
