package admin.adminsiteserver.member.auth.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static admin.adminsiteserver.member.auth.exception.AuthExceptionType.JWT_TOKEN_EXPIRED;

@Getter
public class JwtTokenExpiredException extends BaseException {
    public JwtTokenExpiredException() {
        super(JWT_TOKEN_EXPIRED.getMessage(), LocalDateTime.now(), JWT_TOKEN_EXPIRED.getStatus());
    }
}
