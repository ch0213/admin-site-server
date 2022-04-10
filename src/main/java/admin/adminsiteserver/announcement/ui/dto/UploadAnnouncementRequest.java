package admin.adminsiteserver.announcement.ui.dto;

import admin.adminsiteserver.announcement.domain.Announcement;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class UploadAnnouncementRequest extends BaseAnnouncementRequest{
    public UploadAnnouncementRequest(String title, String content, List<MultipartFile> files) {
        super(title, content, files);
    }

    public Announcement createAnnouncement(LoginUserInfo loginUserInfo) {
        return Announcement.builder()
                .authorId(loginUserInfo.getUserId())
                .authorName(loginUserInfo.getName())
                .title(getTitle())
                .content(getContent())
                .build();
    }
}
