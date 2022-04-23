package admin.adminsiteserver.announcement.ui.dto;

import admin.adminsiteserver.announcement.domain.Announcement;
import admin.adminsiteserver.announcement.domain.AnnouncementFilePath;
import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class UploadAnnouncementRequest extends BaseAnnouncementRequest{
    public UploadAnnouncementRequest(String title, String content, List<FilePathDto> files) {
        super(title, content, files);
    }

    public Announcement createAnnouncement(LoginUserInfo loginUserInfo) {
        List<AnnouncementFilePath> filePaths = new ArrayList<>();
        if (getFiles() != null) {
            filePaths = getFiles().stream()
                    .map(filePathDto -> filePathDto.toFilePath(AnnouncementFilePath.class))
                    .collect(Collectors.toList());
        }

        return Announcement.builder()
                .authorId(loginUserInfo.getUserId())
                .authorName(loginUserInfo.getName())
                .title(getTitle())
                .content(getContent())
                .build();
    }
}
