package admin.adminsiteserver.gallery.domain;

import admin.adminsiteserver.gallery.exception.NotExistGalleryCommentException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class GalleryComments {

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "gallery_id")
    private List<GalleryComment> comments = new ArrayList<>();

    public void addComment(GalleryComment comment) {
        this.comments.add(comment);
    }

    public GalleryComment findUpdateComment(Long commentId) {
        return comments.stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findAny()
                .orElseThrow(NotExistGalleryCommentException::new);
    }

    public void deleteComment(GalleryComment comment) {
        comments.remove(comment);
    }
}
