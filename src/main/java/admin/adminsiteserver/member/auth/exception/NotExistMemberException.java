package admin.adminsiteserver.member.auth.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static admin.adminsiteserver.member.auth.exception.AuthExceptionType.NOT_EXIST_MEMBER;

@Getter
public class NotExistMemberException extends BaseException {
    public NotExistMemberException() {
        super(NOT_EXIST_MEMBER.getMessage(), LocalDateTime.now(), NOT_EXIST_MEMBER.getStatus());
    }
}
