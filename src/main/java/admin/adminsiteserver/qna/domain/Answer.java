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

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "qna_id")
    private Qna qna;


    @OneToMany(mappedBy = "answer", cascade = ALL, orphanRemoval = true)
    private List<AnswerFilePath> images = new ArrayList<>();
}
