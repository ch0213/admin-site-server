package admin.adminsiteserver.announcement.domain;

import admin.adminsiteserver.common.domain.BaseTimeEntity;
import admin.adminsiteserver.common.exception.PermissionDeniedException;
import admin.adminsiteserver.common.vo.Author;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Announcement extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private Author author;

    @Column
    private String title;

    @Lob
    private String content;

    @Column
    private boolean deleted;

    @Embedded
    private AnnouncementFilePaths files = new AnnouncementFilePaths();

    @Embedded
    private AnnouncementComments comments = new AnnouncementComments();

    @Builder
    public Announcement(Author author, String title, String content, List<AnnouncementFilePath> filePaths) {
        this(null, author, title, content, new AnnouncementFilePaths(filePaths), new AnnouncementComments());
    }

    public Announcement(Long id, Author author, String title, String content, AnnouncementFilePaths files) {
        this(id, author, title, content, files, new AnnouncementComments());
    }

    public Announcement(Long id, Author author, String title, String content, AnnouncementFilePaths files, AnnouncementComments comments) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.deleted = false;
        this.files = files;
        this.comments = comments;
    }

    public void update(String title, String content, List<AnnouncementFilePath> files, Author author) {
        validateAuthority(author);
        this.title = title;
        this.content = content;
        this.files.update(files);
    }

    public void updateAuthor(Author author) {
        if (this.author.equalsId(author)) {
            this.author = author;
        }
        comments.updateAuthor(author);
    }

    public void delete(Author author) {
        validateAuthority(author);
        this.deleted = true;
        this.files.deleteAll();
        this.comments.deleteAll();
    }

    public AnnouncementComment addComment(String content, Author author) {
        return comments.add(content, author);
    }

    public void updateComment(Long commentId, String content, Author author) {
        comments.update(commentId, content, author);
    }

    public void deleteComment(Long commentId, Author author) {
        comments.delete(commentId, author);
    }

    public List<AnnouncementComment> getNotDeletedComments() {
        return this.comments.getNotDeletedComments();
    }

    public List<AnnouncementFilePath> getNotDeletedFilePaths() {
        return this.files.getNotDeletedFiles();
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
