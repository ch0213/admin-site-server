package admin.adminsiteserver.authentication.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
public class EmptyEmailException extends BaseException {
    public EmptyEmailException() {
        super("이메일이 존재하지 않습니다.", LocalDateTime.now(), BAD_REQUEST);
    }
}
