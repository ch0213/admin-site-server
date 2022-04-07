package admin.adminsiteserver.member.member.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static admin.adminsiteserver.member.member.exception.MemberExceptionType.NOT_POSSIBLE_DELETE_OTHER;

@Getter
public class NotPossibleDeleteOtherException extends BaseException {
    public NotPossibleDeleteOtherException() {
        super(NOT_POSSIBLE_DELETE_OTHER.getMessage(), LocalDateTime.now(), NOT_POSSIBLE_DELETE_OTHER.getStatus());
    }
}
