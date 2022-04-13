package admin.adminsiteserver.qna.application.dto;

import admin.adminsiteserver.qna.domain.AnswerComment;
import admin.adminsiteserver.qna.domain.QuestionComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerCommentResponse {
    private Long id;
    private String authorId;
    private String authorName;
    private String comment;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public static AnswerCommentResponse from(AnswerComment answerComment) {
        return new AnswerCommentResponse(
                answerComment.getId(),
                answerComment.getAuthorId(),
                answerComment.getAuthorName(),
                answerComment.getComment(),
                answerComment.getCreatedAt(),
                answerComment.getModifiedAt()
        );
    }
}
