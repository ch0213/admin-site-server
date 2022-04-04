package admin.adminsiteserver.qna.exception;

import admin.adminsiteserver.common.exception.BaseException;

import java.time.LocalDateTime;

import static admin.adminsiteserver.qna.exception.QnaExceptionType.NOT_EXIST_QNA;

public class NotExistQnaException extends BaseException {
    public NotExistQnaException() {
        super(NOT_EXIST_QNA.getMessage(), LocalDateTime.now(), NOT_EXIST_QNA.getStatus());
    }
}
