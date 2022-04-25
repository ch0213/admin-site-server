package admin.adminsiteserver.announcement.domain;

import admin.adminsiteserver.announcement.exception.NotExistAnnouncementCommentException;
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
public class AnnouncementComments {

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "announcement_id")
    private List<AnnouncementComment> comments = new ArrayList<>();

    public void addComment(AnnouncementComment comment) {
        this.comments.add(comment);
    }

    public AnnouncementComment findUpdateComment(Long commentId) {
        return comments.stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findAny()
                .orElseThrow(NotExistAnnouncementCommentException::new);
    }

    public void deleteComment(AnnouncementComment comment) {
        comments.remove(comment);
    }
}
