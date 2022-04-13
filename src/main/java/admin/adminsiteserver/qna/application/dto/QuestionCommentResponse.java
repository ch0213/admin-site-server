package admin.adminsiteserver.qna.application.dto;

import admin.adminsiteserver.qna.domain.QuestionComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCommentResponse {

    private Long id;
    private String authorId;
    private String authorName;
    private String comment;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public static QuestionCommentResponse from(QuestionComment questionComment) {
        return new QuestionCommentResponse(
                questionComment.getId(),
                questionComment.getAuthorId(),
                questionComment.getAuthorName(),
                questionComment.getComment(),
                questionComment.getCreatedAt(),
                questionComment.getModifiedAt()
        );
    }
}
