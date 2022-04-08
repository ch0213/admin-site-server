package admin.adminsiteserver.levelup.exception;

import admin.adminsiteserver.common.exception.BaseException;

import java.time.LocalDateTime;

import static admin.adminsiteserver.levelup.exception.LevelUpExceptionType.NOT_EXIST_ROLE;

public class NotExistRoleException extends BaseException {
    public NotExistRoleException() {
        super(NOT_EXIST_ROLE.getMessage(), LocalDateTime.now(), NOT_EXIST_ROLE.getStatus());
    }
}
