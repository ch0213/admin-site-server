package admin.adminsiteserver.authentication.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static admin.adminsiteserver.authentication.exception.AuthExceptionType.PERMISSION_DENIED;

@Getter
public class PermissionDeniedException extends BaseException {
    public PermissionDeniedException() {
        super(PERMISSION_DENIED.getMessage(), LocalDateTime.now(), PERMISSION_DENIED.getStatus());
    }
}
