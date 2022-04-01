package admin.adminsiteserver.post.announcement.application;

import admin.adminsiteserver.common.domain.FilePath;
import admin.adminsiteserver.common.dto.FilePathDto;
import admin.adminsiteserver.post.announcement.domain.Announcement;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class AnnouncementDto {
    private Long id;
    private String authorId;
    private String authorName;
    private String title;
    private String content;
    private List<FilePathDto> images;

    public static AnnouncementDto from(Announcement announcement) {
        return new AnnouncementDto(
                announcement.getId(),
                announcement.getAuthorId(),
                announcement.getAuthorName(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getImages().stream()
                        .map(FilePathDto::from)
                        .collect(Collectors.toList())
        );
    }
}
