package admin.adminsiteserver.announcement.application.dto;

import admin.adminsiteserver.aws.dto.response.FilePath;
import admin.adminsiteserver.announcement.domain.Announcement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementResponse {
    private Long id;
    private String authorEmail;
    private String authorName;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;
    private List<FilePath> files;
    private List<AnnouncementCommentResponse> comments;

    public static AnnouncementResponse from(Announcement announcement) {
        return new AnnouncementResponse(
                announcement.getId(),
                announcement.getAuthorEmail(),
                announcement.getAuthorName(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getCreatedAt(),
                announcement.getModifiedAt(),
                announcement.getFiles().getFiles().stream()
                        .map(FilePath::from)
                        .collect(Collectors.toList()),
                announcement.getComments().getComments().stream()
                        .map(AnnouncementCommentResponse::from)
                        .collect(Collectors.toList())
        );
    }
}
