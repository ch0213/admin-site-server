package admin.adminsiteserver.announcement.ui.dto;

import admin.adminsiteserver.announcement.domain.AnnouncementComment;
import admin.adminsiteserver.authentication.ui.LoginUserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class AnnouncementCommentRequest {
    @NotNull(message = "댓글을 입력해주세요.")
    private String comment;

    public AnnouncementComment toAnnouncementComment(LoginUserInfo loginUserInfo) {
        return new AnnouncementComment(
                null,
                null,
                null
        );
    }
}
