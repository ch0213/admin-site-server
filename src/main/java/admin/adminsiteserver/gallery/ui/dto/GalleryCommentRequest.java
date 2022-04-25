package admin.adminsiteserver.gallery.ui.dto;

import admin.adminsiteserver.gallery.domain.GalleryComment;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class GalleryCommentRequest {
    @NotNull(message = "댓글을 입력해주세요.")
    private String comment;

    public GalleryComment toGalleryComment(LoginUserInfo loginUserInfo) {
        return new GalleryComment(
                loginUserInfo.getEmail(),
                loginUserInfo.getName(),
                comment
        );
    }
}
