package admin.adminsiteserver.announcement.ui.dto;

import admin.adminsiteserver.announcement.domain.Announcement;
import admin.adminsiteserver.aws.dto.response.FilePath;
import admin.adminsiteserver.authentication.ui.LoginUserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UploadAnnouncementRequest extends BaseAnnouncementRequest{
    public UploadAnnouncementRequest(String title, String content, List<FilePath> files) {
        super(title, content, files);
    }

    public Announcement createAnnouncement(LoginUserInfo loginUserInfo) {
        return Announcement.builder()
                .authorEmail(loginUserInfo.getEmail())
                .authorName(loginUserInfo.getName())
                .title(getTitle())
                .content(getContent())
                .build();
    }
}
