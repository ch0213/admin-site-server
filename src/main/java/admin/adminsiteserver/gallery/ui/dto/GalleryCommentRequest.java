package admin.adminsiteserver.gallery.ui.dto;

import admin.adminsiteserver.gallery.domain.GalleryComment;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GalleryCommentRequest {
    private String comment;

    public GalleryComment toGalleryComment(LoginUserInfo loginUserInfo) {
        return new GalleryComment(
                loginUserInfo.getUserId(),
                loginUserInfo.getName(),
                comment
        );
    }
}
