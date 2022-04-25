package admin.adminsiteserver.qna.application.dto;

import admin.adminsiteserver.qna.domain.Qna;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QnaSimpleResponse {
    private Long id;
    private String authorEmail;
    private String authorName;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;

    public static QnaSimpleResponse from(Qna qna) {
        return new QnaSimpleResponse(
                qna.getId(),
                qna.getAuthorEmail(),
                qna.getAuthorName(),
                qna.getTitle(),
                qna.getContent(),
                qna.getCreatedAt(),
                qna.getModifiedAt()
        );
    }
}
