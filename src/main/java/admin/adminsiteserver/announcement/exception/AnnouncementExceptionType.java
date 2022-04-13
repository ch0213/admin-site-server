package admin.adminsiteserver.announcement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum AnnouncementExceptionType {
    NOT_EXIST_ANNOUNCEMENT("존재하지 않는 공지사항입니다.", BAD_REQUEST),
    UNAUTHORIZED_FOR_ANNOUNCEMENT("공지사항에 대한 권한이 없습니다.", UNAUTHORIZED),
    NOT_EXIST_ANNOUNCEMENT_COMMENT("존재하지 않는 댓글입니다.", BAD_REQUEST),
    UNAUTHORIZED_FOR_ANNOUNCEMENT_COMMENT("댓글에 대한 권한이 없습니다.", UNAUTHORIZED);

    private final String message;
    private HttpStatus status;
}
