package admin.adminsiteserver.qna.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static admin.adminsiteserver.qna.exception.QnaExceptionType.NOT_EXIST_QNA_COMMENT;

@Getter
public class NotExistQuestionCommentException extends BaseException {
    public NotExistQuestionCommentException() {
        super(NOT_EXIST_QNA_COMMENT.getMessage(), LocalDateTime.now(), NOT_EXIST_QNA_COMMENT.getStatus());
    }
}
