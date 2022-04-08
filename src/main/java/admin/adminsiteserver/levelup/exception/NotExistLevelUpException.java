package admin.adminsiteserver.levelup.exception;

import admin.adminsiteserver.common.exception.BaseException;

import java.time.LocalDateTime;

import static admin.adminsiteserver.levelup.exception.LevelUpExceptionType.NOT_EXIST_LEVELUP;

public class NotExistLevelUpException extends BaseException {
    public NotExistLevelUpException() {
        super(NOT_EXIST_LEVELUP.getMessage(), LocalDateTime.now(), NOT_EXIST_LEVELUP.getStatus());
    }
}
