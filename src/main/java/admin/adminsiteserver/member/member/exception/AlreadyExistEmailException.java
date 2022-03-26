package admin.adminsiteserver.member.member.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AlreadyExistEmailException extends BaseException {
    public AlreadyExistEmailException(MemberExceptionType memberExceptionType) {
        super(memberExceptionType.getMessage(), LocalDateTime.now(), memberExceptionType.getStatus());
    }
}
