package admin.adminsiteserver.member.auth.exception;

import admin.adminsiteserver.common.exception.BaseException;

import java.time.LocalDateTime;

public class JwtTokenNotExistException extends BaseException {
    public JwtTokenNotExistException(AuthExceptionType authExceptionType) {
        super(authExceptionType.getMessage(), LocalDateTime.now(), authExceptionType.getStatus());
    }
}
