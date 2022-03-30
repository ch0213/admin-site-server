package admin.adminsiteserver.post.announcement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum AnnouncementExceptionType {
    NOT_EXIST_ANNOUNCEMENT("존재하지 않는 공지사항입니다.", BAD_REQUEST);

    private final String message;
    private HttpStatus status;
}
