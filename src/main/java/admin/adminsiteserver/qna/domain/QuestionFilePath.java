package admin.adminsiteserver.qna.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class QuestionFilePath {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String fileName;
    private String fileUrl;

    public QuestionFilePath(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}
