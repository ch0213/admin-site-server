package admin.adminsiteserver.announcement.application.dto;

import admin.adminsiteserver.announcement.domain.Announcement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementSimpleResponse {
    private Long id;
    private String authorId;
    private String authorName;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;

    public static AnnouncementSimpleResponse from(Announcement announcement) {
        return new AnnouncementSimpleResponse(
                announcement.getId(),
                announcement.getAuthorEmail(),
                announcement.getAuthorName(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getCreatedAt(),
                announcement.getModifiedAt()
        );
    }
}
