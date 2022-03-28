package admin.adminsiteserver.member.auth.exception;

import admin.adminsiteserver.common.exception.BaseException;

import java.time.LocalDateTime;

import static admin.adminsiteserver.member.auth.exception.AuthExceptionType.JWT_TOKEN_NOT_EXIST;

public class JwtTokenNotExistException extends BaseException {
    public JwtTokenNotExistException() {
        super(JWT_TOKEN_NOT_EXIST.getMessage(), LocalDateTime.now(), JWT_TOKEN_NOT_EXIST.getStatus());
    }
}
