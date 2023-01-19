package admin.adminsiteserver.common.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class RoleNotFoundException extends BaseException {
    public RoleNotFoundException() {
        super("존재하지 않는 역할입니다.", LocalDateTime.now(), HttpStatus.BAD_REQUEST);
    }
}
