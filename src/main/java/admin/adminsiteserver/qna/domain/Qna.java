package admin.adminsiteserver.qna.domain;

import admin.adminsiteserver.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.common.domain.BaseTimeEntity;
import admin.adminsiteserver.qna.domain.answer.Answer;
import admin.adminsiteserver.qna.domain.answer.Answers;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Qna extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String authorEmail;
    private String authorName;
    private String title;

    @Lob
    private String content;

    @Embedded
    private QuestionFilePaths files = new QuestionFilePaths();

    @Embedded
    private Answers answers = new Answers();

    @Embedded
    private QuestionComments comments = new QuestionComments();

    @Builder
    public Qna(Long id, String authorEmail, String authorName, String title, String content) {
        this.id = id;
        this.authorEmail = authorEmail;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
    }

    public void updateContentAndTitle(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void saveFilePaths(List<QuestionFilePath> filePaths) {
        files.saveFilePaths(filePaths);
    }

    public void addAnswer(Answer answer) {
        this.answers.addAnswer(answer);
    }

    public void deleteAnswer(Answer answer) {
        this.answers.deleteAnswer(answer);
    }

    public Answer findAnswer(Long id) {
        return this.answers.findAnswer(id);
    }

    public List<FilePathDto> findDeleteFilePaths() {
        return files.findDeleteFilePaths();
    }

    public void deleteFilePaths(List<FilePathDto> deleteFileUrls) {
        files.deleteFiles(deleteFileUrls);
    }

    public void addComment(QuestionComment comment) {
        comments.addComment(comment);
    }

    public void deleteComment(QuestionComment comment) {
        comments.deleteComment(comment);
    }

    public QuestionComment findUpdateOrDeleteComment(Long commentId) {
        return comments.findUpdateComment(commentId);
    }
}
