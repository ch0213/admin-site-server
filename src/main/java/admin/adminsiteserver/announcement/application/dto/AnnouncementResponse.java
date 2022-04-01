package admin.adminsiteserver.announcement.application.dto;

import admin.adminsiteserver.common.dto.FilePathDto;
import admin.adminsiteserver.announcement.domain.Announcement;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class AnnouncementResponse {
    private String authorId;
    private String authorName;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;
    private List<FilePathDto> image;

    public static AnnouncementResponse of(Announcement announcement, List<FilePathDto> image) {
        return new AnnouncementResponse(
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
