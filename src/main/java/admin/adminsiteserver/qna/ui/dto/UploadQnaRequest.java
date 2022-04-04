package admin.adminsiteserver.qna.ui.dto;

import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.qna.domain.Qna;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class UploadQnaRequest extends BaseQnaRequest{
    public UploadQnaRequest(String title, String content, List<MultipartFile> images) {
        super(title, content, images);
    }

    public Qna createQna(LoginUserInfo loginUserInfo) {
        return Qna.builder()
                .authorId(loginUserInfo.getUserId())
                .authorName(loginUserInfo.getName())
                .title(getTitle())
                .content(getContent())
                .build();
    }
}
