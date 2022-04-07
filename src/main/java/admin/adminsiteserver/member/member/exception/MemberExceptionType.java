package admin.adminsiteserver.member.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum MemberExceptionType {
    ALREADY_EXIST_USER_ID("이미 존재하는 아이디입니다.", BAD_REQUEST),
    NOT_EXIST_USER("존재하지 않는 유저입니다.", BAD_REQUEST);

    private final String message;
    private HttpStatus status;
}
