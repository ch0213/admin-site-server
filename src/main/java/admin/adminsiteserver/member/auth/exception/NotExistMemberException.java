package admin.adminsiteserver.member.auth.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotExistMemberException extends BaseException {
    public NotExistMemberException(AuthExceptionType authExceptionType) {
        super(authExceptionType.getMessage(), LocalDateTime.now(), authExceptionType.getStatus());
    }
}
