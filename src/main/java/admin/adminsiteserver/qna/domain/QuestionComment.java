package admin.adminsiteserver.qna.domain;

import admin.adminsiteserver.common.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class QuestionComment extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String authorEmail;
    private String authorName;
    private String comment;

    public QuestionComment(String authorEmail, String authorName, String comment) {
        this.authorEmail = authorEmail;
        this.authorName = authorName;
        this.comment = comment;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }
}
