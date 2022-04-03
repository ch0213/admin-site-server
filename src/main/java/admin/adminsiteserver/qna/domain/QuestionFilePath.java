package admin.adminsiteserver.qna.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

import static lombok.AccessLevel.*;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class QuestionFilePath {

    @Id @GeneratedValue
    private Long id;
    private String fileName;
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "qna")
    private Qna qna;

    public QuestionFilePath(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public void includedToQna(Qna qna) {
        this.qna = qna;
    }
}
