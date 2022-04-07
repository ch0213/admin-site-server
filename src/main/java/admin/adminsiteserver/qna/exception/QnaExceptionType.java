package admin.adminsiteserver.qna.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum QnaExceptionType {
    NOT_EXIST_QNA("존재하지 않는 Q&A 입니다.", BAD_REQUEST),
    NOT_EXIST_ANSWER("존재하지 않는 답변입니다.", BAD_REQUEST),
    UNAUTHORIZED_FOR_QNA("질문에 대한 권한이 없습니다.", UNAUTHORIZED),
    UNAUTHORIZED_FOR_ANSWER("답변에 대한 권한이 없습니다.", UNAUTHORIZED);

    private final String message;
    private HttpStatus status;
}
