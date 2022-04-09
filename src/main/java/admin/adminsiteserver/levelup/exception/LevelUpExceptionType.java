package admin.adminsiteserver.levelup.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum LevelUpExceptionType {
    NOT_EXIST_ROLE("존재하지 않는 역할입니다.", BAD_REQUEST),
    NOT_EXIST_LEVELUP("존재하지 신청입니다.", BAD_REQUEST),
    NOT_AUTHORIZATION_LEVELUP("이 권한 신청에 대한 접근권한이 없습니다.", UNAUTHORIZED),
    ALREADY_EXIST_LEVELUP("이미 신청했습니다.", BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
