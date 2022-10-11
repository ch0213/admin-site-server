package admin.adminsiteserver.announcement.domain;

import admin.adminsiteserver.common.domain.BaseTimeEntity;
import admin.adminsiteserver.common.exception.PermissionDeniedException;
import admin.adminsiteserver.common.vo.Author;
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

    private String comment;

    private boolean deleted;

    @Embedded
    private Author author;

    public AnnouncementComment(String comment, Author author) {
        this(null, comment, author);
    }

    public AnnouncementComment(Long id, String comment, Author author) {
        this.id = id;
        this.comment = comment;
        this.deleted = false;
        this.author = author;
    }

    public void update(String comment, Author author) {
        validateAuthority(author);
        this.comment = comment;
    }

    public void delete(Author author) {
        validateAuthority(author);
        this.deleted = true;
    }

    public void forceDelete() {
        this.deleted = true;
    }

    public boolean notDeleted() {
        return !this.deleted;
    }

    private void validateAuthority(Author author) {
        if (!hasAuthority(author)) {
            throw new PermissionDeniedException();
        }
    }

    private boolean hasAuthority(Author author) {
        return this.author.equals(author) || this.author.compareTo(author) > 0;
    }
}
