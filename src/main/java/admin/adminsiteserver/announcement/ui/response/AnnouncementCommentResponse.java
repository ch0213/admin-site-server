package admin.adminsiteserver.announcement.ui.response;

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
    private Long authorId;
    private String authorStudentNumber;
    private String authorName;
    private String authorRole;
    private String comment;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public static AnnouncementCommentResponse from(AnnouncementComment announcementComment) {
        return new AnnouncementCommentResponse(
                announcementComment.getId(),
                announcementComment.getAuthor().getMemberId(),
                announcementComment.getAuthor().getStudentNumber(),
                announcementComment.getAuthor().getName(),
                announcementComment.getAuthor().getRoleType().getRole(),
                announcementComment.getComment(),
                announcementComment.getCreatedAt(),
                announcementComment.getModifiedAt()
        );
    }
}
