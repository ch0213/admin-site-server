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
public class AnswerFilePath {

    @Id @GeneratedValue
    private Long id;
    private String fileName;
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    public AnswerFilePath(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}
