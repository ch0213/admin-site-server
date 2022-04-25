package admin.adminsiteserver.gallery.application.dto;

import admin.adminsiteserver.gallery.domain.GalleryComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GalleryCommentResponse {

    private Long id;
    private String authorId;
    private String authorName;
    private String comment;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public static GalleryCommentResponse from(GalleryComment galleryComment) {
        return new GalleryCommentResponse(
                galleryComment.getId(),
                galleryComment.getAuthorEmail(),
                galleryComment.getAuthorName(),
                galleryComment.getComment(),
                galleryComment.getCreatedAt(),
                galleryComment.getModifiedAt()
        );
    }
}
