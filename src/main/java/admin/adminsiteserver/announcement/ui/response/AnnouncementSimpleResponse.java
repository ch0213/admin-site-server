package admin.adminsiteserver.announcement.ui.response;

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
    private Long authorId;
    private String authorStudentNumber;
    private String authorName;
    private String authorRole;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public static AnnouncementSimpleResponse from(Announcement announcement) {
        return new AnnouncementSimpleResponse(
                announcement.getId(),
                announcement.getAuthor().getMemberId(),
                announcement.getAuthor().getStudentNumber(),
                announcement.getAuthor().getName(),
                announcement.getAuthor().getRoleType().getRole(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getCreatedAt(),
                announcement.getModifiedAt()
        );
    }
}
