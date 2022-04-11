package admin.adminsiteserver.announcement.application.dto;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
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
    private String authorId;
    private String authorName;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;
    private List<FilePathDto> files;

    public static AnnouncementResponse of(Announcement announcement, List<FilePathDto> files) {
        return new AnnouncementResponse(
                announcement.getId(),
                announcement.getAuthorId(),
                announcement.getAuthorName(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getCreatedAt(),
                announcement.getModifiedAt(),
                files
        );
    }

    public static AnnouncementResponse from(Announcement announcement) {
        return new AnnouncementResponse(
                announcement.getId(),
                announcement.getAuthorId(),
                announcement.getAuthorName(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getCreatedAt(),
                announcement.getModifiedAt(),
                announcement.getFiles().stream()
                        .map(FilePathDto::from)
                        .collect(Collectors.toList())
        );
    }
}
