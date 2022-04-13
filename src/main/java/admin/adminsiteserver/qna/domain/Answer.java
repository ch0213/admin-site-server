package admin.adminsiteserver.qna.domain;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Lob
    private String content;

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "answer_id")
    private List<AnswerFilePath> files = new ArrayList<>();

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "answer_id")
    private List<AnswerComment> comments = new ArrayList<>();

    public Answer(String authorId, String authorName, String content, List<AnswerFilePath> files) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.content = content;
        this.files = files;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void addImages(List<AnswerFilePath> images) {
        if (images == null) {
            return;
        }
        this.files.addAll(images);
    }

    public void deleteFiles(List<FilePathDto> deleteFileUrls) {
        files.removeIf(filePath -> deleteFileUrls.stream().map(FilePathDto::getFileUrl)
                .collect(Collectors.toList())
                .contains(filePath.getFileUrl()));
    }

    public void addComment(AnswerComment comment) {
        this.comments.add(comment);
    }
}
