package admin.adminsiteserver.gallery.application.dto;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.gallery.domain.Gallery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GalleryResponse {
    private Long id;
    private String authorId;
    private String authorName;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;
    private List<FilePathDto> files;
    private List<GalleryCommentResponse> comments;

    public static GalleryResponse from(Gallery gallery) {
        return new GalleryResponse(
                gallery.getId(),
                gallery.getAuthorEmail(),
                gallery.getAuthorName(),
                gallery.getTitle(),
                gallery.getContent(),
                gallery.getCreatedAt(),
                gallery.getModifiedAt(),
                gallery.getFiles().getFiles().stream()
                        .map(FilePathDto::from)
                        .collect(Collectors.toList()),
                gallery.getComments().getComments().stream()
                        .map(GalleryCommentResponse::from)
                        .collect(Collectors.toList())
        );
    }
}
