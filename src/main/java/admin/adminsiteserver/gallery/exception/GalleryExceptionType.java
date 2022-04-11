package admin.adminsiteserver.gallery.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum GalleryExceptionType {
    NOT_EXIST_GALLERY("존재하지 않는 공지사항입니다.", BAD_REQUEST),
    UNAUTHORIZED_FOR_GALLERY("공지사항에 대한 권한이 없습니다.", UNAUTHORIZED);

    private final String message;
    private HttpStatus status;
}
