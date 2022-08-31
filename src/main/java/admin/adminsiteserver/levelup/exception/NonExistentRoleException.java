package admin.adminsiteserver.levelup.exception;

import admin.adminsiteserver.common.exception.BaseException;

import java.time.LocalDateTime;

import static admin.adminsiteserver.levelup.exception.LevelUpExceptionType.NOT_EXIST_ROLE;

public class NonExistentRoleException extends BaseException {
    public NonExistentRoleException() {
        super(NOT_EXIST_ROLE.getMessage(), LocalDateTime.now(), NOT_EXIST_ROLE.getStatus());
    }
}
