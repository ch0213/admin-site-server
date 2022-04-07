package admin.adminsiteserver.qna.exception;

import admin.adminsiteserver.common.exception.BaseException;

import java.time.LocalDateTime;

import static admin.adminsiteserver.qna.exception.QnaExceptionType.UNAUTHORIZED_FOR_QNA;

public class UnauthorizedForQnaException extends BaseException {
    public UnauthorizedForQnaException() {
        super(UNAUTHORIZED_FOR_QNA.getMessage(), LocalDateTime.now(), UNAUTHORIZED_FOR_QNA.getStatus());
    }
}
