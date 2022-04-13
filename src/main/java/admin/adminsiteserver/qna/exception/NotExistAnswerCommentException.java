package admin.adminsiteserver.qna.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static admin.adminsiteserver.qna.exception.QnaExceptionType.NOT_EXIST_ANSWER_COMMENT;

@Getter
public class NotExistAnswerCommentException extends BaseException {
    public NotExistAnswerCommentException() {
        super(NOT_EXIST_ANSWER_COMMENT.getMessage(), LocalDateTime.now(), NOT_EXIST_ANSWER_COMMENT.getStatus());
    }
}
