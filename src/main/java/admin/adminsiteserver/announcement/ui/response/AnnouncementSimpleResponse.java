package admin.adminsiteserver.announcement.ui.response;

import admin.adminsiteserver.announcement.domain.Announcement;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createAt;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
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
