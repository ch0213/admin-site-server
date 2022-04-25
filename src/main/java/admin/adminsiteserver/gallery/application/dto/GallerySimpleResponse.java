package admin.adminsiteserver.gallery.application.dto;

import admin.adminsiteserver.gallery.domain.Gallery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GallerySimpleResponse {
    private Long id;
    private String authorEmail;
    private String authorName;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;

    public static GallerySimpleResponse from(Gallery gallery) {
        return new GallerySimpleResponse(
                gallery.getId(),
                gallery.getAuthorEmail(),
                gallery.getAuthorName(),
                gallery.getTitle(),
                gallery.getContent(),
                gallery.getCreatedAt(),
                gallery.getModifiedAt()
        );
    }
}
