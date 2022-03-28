package admin.adminsiteserver.member.auth.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WrongPasswordException extends BaseException {
    public WrongPasswordException(AuthExceptionType authExceptionType) {
        super(authExceptionType.getMessage(), LocalDateTime.now(), authExceptionType.getStatus());
    }
}
