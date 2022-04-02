package admin.adminsiteserver.qna.domain;

import admin.adminsiteserver.common.domain.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Qna extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private Question question;

    @OneToMany(mappedBy = "qna")
    private List<Answer> answers = new ArrayList<>();
}
