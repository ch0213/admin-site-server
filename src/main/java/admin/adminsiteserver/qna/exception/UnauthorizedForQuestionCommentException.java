package admin.adminsiteserver.qna.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static admin.adminsiteserver.qna.exception.QnaExceptionType.UNAUTHORIZED_FOR_QNA_COMMENT;

@Getter
public class UnauthorizedForQuestionCommentException extends BaseException {
    public UnauthorizedForQuestionCommentException() {
        super(UNAUTHORIZED_FOR_QNA_COMMENT.getMessage(), LocalDateTime.now(), UNAUTHORIZED_FOR_QNA_COMMENT.getStatus());
    }
}
