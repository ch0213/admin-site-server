package admin.adminsiteserver.levelup.exception;

import admin.adminsiteserver.common.exception.BaseException;
import admin.adminsiteserver.levelup.domain.LevelUp;

import java.time.LocalDateTime;

import static admin.adminsiteserver.levelup.exception.LevelUpExceptionType.ALREADY_EXIST_LEVELUP;

public class AlreadyExistLevelUpException extends BaseException {
    public AlreadyExistLevelUpException() {
        super(ALREADY_EXIST_LEVELUP.getMessage(), LocalDateTime.now(), ALREADY_EXIST_LEVELUP.getStatus());
    }
}
