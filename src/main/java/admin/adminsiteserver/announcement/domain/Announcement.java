package admin.adminsiteserver.announcement.domain;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.common.domain.BaseTimeEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Announcement extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String authorEmail;
    private String authorName;
    private String title;

    @Lob
    private String content;

    @Embedded
    private AnnouncementFilePaths files = new AnnouncementFilePaths();

    @Embedded
    private AnnouncementComments comments = new AnnouncementComments();

    @Builder
    public Announcement(String authorEmail, String authorName, String title, String content) {
        this.authorEmail = authorEmail;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
    }

    public void updateTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void saveFilePaths(List<AnnouncementFilePath> filePaths) {
        files.saveFilePaths(filePaths);
    }

    public List<FilePathDto> findDeleteFilePaths() {
        return files.findDeleteFilePaths();
    }

    public void deleteFilePaths(List<FilePathDto> deleteFileUrls) {
        files.deleteFiles(deleteFileUrls);
    }

    public void addComment(AnnouncementComment comment) {
        comments.addComment(comment);
    }

    public void deleteComment(AnnouncementComment comment) {
        comments.deleteComment(comment);
    }

    public AnnouncementComment findUpdateOrDeleteComment(Long commentId) {
        return comments.findUpdateComment(commentId);
    }
}
