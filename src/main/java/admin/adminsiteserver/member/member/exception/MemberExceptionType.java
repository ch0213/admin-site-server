package admin.adminsiteserver.member.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum MemberExceptionType {
    ALREADY_EXIST_EMAIL("이미 존재하는 회원입니다.", BAD_REQUEST);

    private final String message;
    private HttpStatus status;
}
