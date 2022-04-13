package admin.adminsiteserver.announcement.ui.dto;

import admin.adminsiteserver.announcement.domain.AnnouncementComment;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnnouncementCommentRequest {
    private String comment;

    public AnnouncementComment toAnnouncementComment(LoginUserInfo loginUserInfo) {
        return new AnnouncementComment(
                loginUserInfo.getUserId(),
                loginUserInfo.getName(),
                comment
        );
    }
}
