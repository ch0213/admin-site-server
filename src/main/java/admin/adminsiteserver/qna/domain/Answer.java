package admin.adminsiteserver.qna.domain;

import admin.adminsiteserver.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.*;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Answer extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    private String authorId;

    private String authorName;

    private String content;

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "answer_id")
    private List<AnswerFilePath> images = new ArrayList<>();

    public Answer(String authorId, String authorName, String content) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.content = content;
    }

    public void addImages(List<AnswerFilePath> images) {
        if (images == null) {
            return;
        }
        this.images.addAll(images);
    }

    public void deleteImages(List<String> deleteFileUrls) {
        if (deleteFileUrls == null) {
            return;
        }
        images.removeIf(filePath -> deleteFileUrls.contains(filePath.getFileUrl()));
    }
}
