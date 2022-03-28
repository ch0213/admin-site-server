package admin.adminsiteserver.member.member.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static admin.adminsiteserver.member.member.exception.MemberExceptionType.ALREADY_EXIST_USER_ID;

@Getter
public class AlreadyExistUserIDException extends BaseException {
    public AlreadyExistUserIDException() {
        super(ALREADY_EXIST_USER_ID.getMessage(), LocalDateTime.now(), ALREADY_EXIST_USER_ID.getStatus());
    }
}
