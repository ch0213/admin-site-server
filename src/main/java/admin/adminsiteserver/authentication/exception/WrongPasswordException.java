package admin.adminsiteserver.authentication.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
public class WrongPasswordException extends BaseException {
    public WrongPasswordException() {
        super("비밀번호가 잘못되었습니다.", LocalDateTime.now(), BAD_REQUEST);
    }
}
