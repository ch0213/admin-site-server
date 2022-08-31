package admin.adminsiteserver.authentication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum AuthExceptionType {
    NOT_EXIST_MEMBER("존재하지 않는 계정입니다.", BAD_REQUEST),
    WRONG_PASSWORD("비밀번호가 틀렸습니다.", BAD_REQUEST),
    JWT_TOKEN_EXPIRED("토큰이 만료됐습니다.", UNAUTHORIZED),
    JWT_TOKEN_NOT_EXIST("토큰이 없습니다.", UNAUTHORIZED),
    EMAIL_EMPTY("이메일이 존재하지 않습니다.", UNAUTHORIZED),
    PERMISSION_DENIED("접근 권한이 없습니다.", FORBIDDEN);

    private final String message;
    private HttpStatus status;
}
