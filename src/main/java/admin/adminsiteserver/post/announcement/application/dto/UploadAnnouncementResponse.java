package admin.adminsiteserver.post.announcement.application.dto;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.post.announcement.domain.Announcement;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class UploadAnnouncementResponse {
    private String authorId;
    private String authorName;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;
    private List<FilePathDto> image;

    public static UploadAnnouncementResponse of(
            Announcement announcement,
            List<FilePathDto> image
    ) {
        return new UploadAnnouncementResponse(
                announcement.getAuthorId(),
                announcement.getAuthorName(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getCreatedAt(),
                announcement.getModifiedAt(),
                image
        );
    }
}
