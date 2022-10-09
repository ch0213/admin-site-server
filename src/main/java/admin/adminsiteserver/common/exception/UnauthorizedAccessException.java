package admin.adminsiteserver.common.exception;

import lombok.Getter;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
public class UnauthorizedAccessException extends BaseException {
    public UnauthorizedAccessException() {
        super("인증이 되어있지 않습니다.", LocalDateTime.now(), UNAUTHORIZED);
    }
}
