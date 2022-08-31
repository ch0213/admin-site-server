package admin.adminsiteserver.authentication.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static admin.adminsiteserver.authentication.exception.AuthExceptionType.EMAIL_EMPTY;

@Getter
public class EmailEmptyException extends BaseException {
    public EmailEmptyException() {
        super(EMAIL_EMPTY.getMessage(), LocalDateTime.now(), EMAIL_EMPTY.getStatus());
    }
}
