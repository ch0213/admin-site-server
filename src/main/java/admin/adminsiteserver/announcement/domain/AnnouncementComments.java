package admin.adminsiteserver.announcement.domain;

import admin.adminsiteserver.announcement.exception.AnnouncementCommentNotFoundException;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor
public class AnnouncementComments {
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "announcement_id")
    private List<AnnouncementComment> comments = new ArrayList<>();

    public AnnouncementComments(List<AnnouncementComment> comments) {
        this.comments.addAll(comments);
    }

    public AnnouncementComment add(String content, Author author) {
        AnnouncementComment comment = new AnnouncementComment(content, author);
        comments.add(comment);
        return comment;
    }

    public void update(Long commentId, String content, Author author) {
        AnnouncementComment comment = findById(commentId);
        comment.update(content, author);
    }

    public void exchange(Author author) {
        this.comments.forEach(comment -> comment.exchange(author));
    }

    public void delete(Long commentId, Author author) {
        AnnouncementComment comment = findById(commentId);
        comment.delete(author);
    }

    public void deleteAll() {
        this.comments.forEach(AnnouncementComment::forceDelete);
    }

    public List<AnnouncementComment> getNotDeletedComments() {
        return this.comments.stream()
                .filter(comment -> !comment.isDeleted())
                .collect(Collectors.toUnmodifiableList());
    }

    private AnnouncementComment findById(Long commentId) {
        return comments.stream()
                .filter(comment -> isMatch(commentId, comment))
                .findAny()
                .orElseThrow(AnnouncementCommentNotFoundException::new);
    }

    private boolean isMatch(Long commentId, AnnouncementComment comment) {
        return comment.getId().equals(commentId) && !comment.isDeleted();
    }
}
