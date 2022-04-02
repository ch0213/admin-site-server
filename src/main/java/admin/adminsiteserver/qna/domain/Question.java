package admin.adminsiteserver.qna.domain;

import admin.adminsiteserver.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Question extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    private String authorId;

    private String authorName;

    private String title;

    private String content;

    @OneToOne
    @JoinColumn(name = "question_id")
    private Qna qna;
}
