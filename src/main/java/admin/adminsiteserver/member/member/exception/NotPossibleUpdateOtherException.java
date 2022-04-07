package admin.adminsiteserver.member.member.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static admin.adminsiteserver.member.member.exception.MemberExceptionType.NOT_POSSIBLE_UPDATE_OTHER;

@Getter
public class NotPossibleUpdateOtherException extends BaseException {
    public NotPossibleUpdateOtherException() {
        super(NOT_POSSIBLE_UPDATE_OTHER.getMessage(), LocalDateTime.now(), NOT_POSSIBLE_UPDATE_OTHER.getStatus());
    }
}
