package admin.adminsiteserver.member.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum MemberExceptionType {
    ALREADY_EXIST_USER_ID("이미 존재하는 아이디입니다.", BAD_REQUEST),
    NOT_EXIST_USER("존재하지 않는 유저입니다.", BAD_REQUEST),
    NOT_POSSIBLE_UPDATE_OTHER("본인의 정보만 업데이트할 수 있습니다.", UNAUTHORIZED),
    NOT_POSSIBLE_DELETE_OTHER("본인의 정보만 삭제할 수 있습니다.", UNAUTHORIZED);

    private final String message;
    private HttpStatus status;
}
