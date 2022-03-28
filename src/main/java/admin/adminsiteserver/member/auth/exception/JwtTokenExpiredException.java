package admin.adminsiteserver.member.auth.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class JwtTokenExpiredException extends BaseException {
    public JwtTokenExpiredException(AuthExceptionType authExceptionType) {
        super(authExceptionType.getMessage(), LocalDateTime.now(), authExceptionType.getStatus());
    }
}
