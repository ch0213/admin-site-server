package admin.adminsiteserver.qna.domain;

import admin.adminsiteserver.common.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class QuestionComment extends BaseTimeEntity {
    @Id @GeneratedValue
    private Long id;
    private String authorId;
    private String authorName;
    private String comment;

    public QuestionComment(String authorId, String authorName, String comment) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.comment = comment;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }
}
