package admin.adminsiteserver.common.exception;

import lombok.Getter;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Getter
public class PermissionDeniedException extends BaseException {
    public PermissionDeniedException() {
        super("접근 권한이 없습니다.", LocalDateTime.now(), FORBIDDEN);
    }
}
