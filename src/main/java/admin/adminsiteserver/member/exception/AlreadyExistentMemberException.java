package admin.adminsiteserver.member.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
public class AlreadyExistentMemberException extends BaseException {
    public AlreadyExistentMemberException() {
        super("이미 존재하는 아이디입니다.", LocalDateTime.now(), BAD_REQUEST);
    }
}
