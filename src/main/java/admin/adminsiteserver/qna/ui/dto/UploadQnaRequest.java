package admin.adminsiteserver.qna.ui.dto;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.qna.domain.Qna;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UploadQnaRequest extends BaseQnaRequest {
    public UploadQnaRequest(String title, String content, List<FilePathDto> images) {
        super(title, content, images);
    }

    public Qna createQna(LoginUserInfo loginUserInfo) {
        return Qna.builder()
                .authorEmail(loginUserInfo.getEmail())
                .authorName(loginUserInfo.getName())
                .title(getTitle())
                .content(getContent())
                .build();
    }
}
