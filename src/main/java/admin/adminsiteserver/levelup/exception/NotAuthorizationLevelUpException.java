package admin.adminsiteserver.levelup.exception;

import admin.adminsiteserver.common.exception.BaseException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static admin.adminsiteserver.levelup.exception.LevelUpExceptionType.NOT_AUTHORIZATION_LEVELUP;

public class NotAuthorizationLevelUpException extends BaseException {
    public NotAuthorizationLevelUpException() {
        super(NOT_AUTHORIZATION_LEVELUP.getMessage(), LocalDateTime.now(), NOT_AUTHORIZATION_LEVELUP.getStatus());
    }
}
