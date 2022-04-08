package admin.adminsiteserver.levelup.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum LevelUpExceptionType {
    NOT_EXIST_ROLE("존재하지 않는 역할입니다.", BAD_REQUEST),
    NOT_EXIST_LEVELUP("존재하지 신청입니다.", BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
