package admin.adminsiteserver.announcement.ui.dto;

import admin.adminsiteserver.announcement.domain.AnnouncementComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementCommentResponse {

    private Long id;
    private String authorId;
    private String authorName;
    private String comment;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public static AnnouncementCommentResponse from(AnnouncementComment announcementComment) {
        return new AnnouncementCommentResponse(
                announcementComment.getId(),
                announcementComment.getAuthorId(),
                announcementComment.getAuthorName(),
                announcementComment.getComment(),
                announcementComment.getCreatedAt(),
                announcementComment.getModifiedAt()
        );
    }
}
