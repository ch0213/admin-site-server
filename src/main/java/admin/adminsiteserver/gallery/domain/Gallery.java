package admin.adminsiteserver.gallery.domain;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Gallery extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String authorEmail;
    private String authorName;
    private String title;

    @Lob
    private String content;

    @Embedded
    private GalleryFilePaths files = new GalleryFilePaths();

    @Embedded
    private GalleryComments comments = new GalleryComments();

    @Builder
    public Gallery(String authorEmail, String authorName, String title, String content) {
        this.authorEmail = authorEmail;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
    }

    public void updateTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void saveFilePaths(List<GalleryFilePath> filePaths) {
        files.saveFilePaths(filePaths);
    }

    public List<FilePathDto> findDeleteFilePaths() {
        return files.findDeleteFilePaths();
    }

    public void deleteFilePaths(List<FilePathDto> deleteFileUrls) {
        files.deleteFiles(deleteFileUrls);
    }

    public void addComment(GalleryComment comment) {
        comments.addComment(comment);
    }

    public void deleteComment(GalleryComment comment) {
        comments.deleteComment(comment);
    }

    public GalleryComment findUpdateOrDeleteComment(Long commentId) {
        return comments.findUpdateComment(commentId);
    }
}
