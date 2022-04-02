package admin.adminsiteserver.qna.domain;

import admin.adminsiteserver.common.domain.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
public class Qna extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @OneToOne(cascade = ALL, orphanRemoval = true)
    private Question question;

    @OneToMany(mappedBy = "qna", orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();
}
