package admin.adminsiteserver.announcement.domain;

import admin.adminsiteserver.common.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class AnnouncementComment extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private Content comment;

    private boolean deleted;

    @Embedded
    private Author author;

    public AnnouncementComment(String comment, Author author) {
        this(null, comment, author);
    }

    public AnnouncementComment(Long id, String comment, Author author) {
        this.id = id;
        this.comment = new Content(comment);
        this.deleted = false;
        this.author = author;
    }

    public void update(String comment, Author author) {
        this.author.validate(author);
        this.comment = new Content(comment);
    }

    public void exchange(Author author) {
        if (this.author.equalsId(author)) {
            this.author = author;
        }
    }

    public void delete(Author author) {
        this.author.validate(author);
        this.deleted = true;
    }

    public void forceDelete() {
        this.deleted = true;
    }

    public boolean isMatch(Long id) {
        return this.id.equals(id) && !deleted;
    }

    public String getComment() {
        return comment.getContent();
    }
}
