package admin.adminsiteserver.authentication.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static admin.adminsiteserver.authentication.exception.AuthExceptionType.WRONG_PASSWORD;

@Getter
public class WrongPasswordException extends BaseException {
    public WrongPasswordException() {
        super(WRONG_PASSWORD.getMessage(), LocalDateTime.now(), WRONG_PASSWORD.getStatus());
    }
}
