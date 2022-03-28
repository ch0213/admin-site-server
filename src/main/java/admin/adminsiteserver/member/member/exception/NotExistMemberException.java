package admin.adminsiteserver.member.member.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static admin.adminsiteserver.member.member.exception.MemberExceptionType.NOT_EXIST_USER;

@Getter
public class NotExistMemberException extends BaseException {
    public NotExistMemberException() {
        super(NOT_EXIST_USER.getMessage(), LocalDateTime.now(), NOT_EXIST_USER.getStatus());
    }
}
