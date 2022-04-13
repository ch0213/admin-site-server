package admin.adminsiteserver.qna.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static admin.adminsiteserver.qna.exception.QnaExceptionType.UNAUTHORIZED_FOR_ANSWER_COMMENT;

@Getter
public class UnauthorizedForAnswerCommentException extends BaseException {
    public UnauthorizedForAnswerCommentException() {
        super(UNAUTHORIZED_FOR_ANSWER_COMMENT.getMessage(), LocalDateTime.now(), UNAUTHORIZED_FOR_ANSWER_COMMENT.getStatus());
    }
}
