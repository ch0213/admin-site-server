package admin.adminsiteserver.member.member.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AlreadyExistUserIDException extends BaseException {
    public AlreadyExistUserIDException(MemberExceptionType memberExceptionType) {
        super(memberExceptionType.getMessage(), LocalDateTime.now(), memberExceptionType.getStatus());
    }
}
