package admin.adminsiteserver.qna.exception;

import admin.adminsiteserver.common.exception.BaseException;

import java.time.LocalDateTime;

import static admin.adminsiteserver.qna.exception.QnaExceptionType.NOT_EXIST_ANSWER;

public class NotExistAnswerException extends BaseException {
    public NotExistAnswerException() {
        super(NOT_EXIST_ANSWER.getMessage(), LocalDateTime.now(), NOT_EXIST_ANSWER.getStatus());
    }
}
