package admin.adminsiteserver.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private final String message;
    private final LocalDateTime timestamp;
    private final HttpStatus status;
}
