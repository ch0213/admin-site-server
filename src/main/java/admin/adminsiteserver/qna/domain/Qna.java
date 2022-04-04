package admin.adminsiteserver.qna.domain;

import admin.adminsiteserver.common.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static lombok.AccessLevel.*;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Qna extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;
    private String authorId;
    private String authorName;
    private String title;
    private String content;

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "qna_id")
    private List<QuestionFilePath> images = new ArrayList<>();

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "qna_id")
    private List<Answer> answers = new ArrayList<>();

    @Builder
    public Qna(Long id, String authorId, String authorName, String title, String content) {
        this.id = id;
        this.authorId = authorId;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
    }

    public void updateContentAndTitle(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addQuestionImages(List<QuestionFilePath> newFilePaths) {
        if (newFilePaths == null) {
            return;
        }
        this.images.addAll(newFilePaths);
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

    public void deleteImages(List<String> deleteFileUrls) {
        if (deleteFileUrls == null) {
            return;
        }
        images.removeIf(filePath -> deleteFileUrls.contains(filePath.getFileUrl()));
    }
}
