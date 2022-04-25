package admin.adminsiteserver.qna.domain.answer;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

import static lombok.AccessLevel.*;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Answer extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    private String authorEmail;

    private String authorName;

    @Lob
    private String content;

    @Embedded
    private AnswerFilePaths files = new AnswerFilePaths();

    @Embedded
    private AnswerComments comments = new AnswerComments();

    @Builder
    public Answer(String authorEmail, String authorName, String content) {
        this.authorEmail = authorEmail;
        this.authorName = authorName;
        this.content = content;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void saveFilePaths(List<AnswerFilePath> filePaths) {
        files.saveFilePaths(filePaths);
    }

    public List<FilePathDto> findDeleteFilePaths() {
        return files.findDeleteFilePaths();
    }

    public void deleteFilePaths(List<FilePathDto> deleteFileUrls) {
        files.deleteFiles(deleteFileUrls);
    }

    public void addComment(AnswerComment comment) {
        comments.addComment(comment);
    }

    public void deleteComment(AnswerComment comment) {
        comments.deleteComment(comment);
    }

    public AnswerComment findUpdateOrDeleteComment(Long commentId) {
        return comments.findUpdateComment(commentId);
    }
}
