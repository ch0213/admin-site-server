package admin.adminsiteserver.qna.exception;

import admin.adminsiteserver.common.exception.BaseException;

import java.time.LocalDateTime;

import static admin.adminsiteserver.qna.exception.QnaExceptionType.UNAUTHORIZED_FOR_ANSWER;

public class UnauthorizedForAnswerException extends BaseException {
    public UnauthorizedForAnswerException() {
        super(UNAUTHORIZED_FOR_ANSWER.getMessage(), LocalDateTime.now(), UNAUTHORIZED_FOR_ANSWER.getStatus());
    }
}
