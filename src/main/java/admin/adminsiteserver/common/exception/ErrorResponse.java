package admin.adminsiteserver.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private String message;
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private Map<String, String> errors;

    private ErrorResponse(String message, LocalDateTime timestamp, int status, String error, Map<String, String> errors) {
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.errors = errors;
    }

    public static ErrorResponse of(String message, Map<String, String> errors) {
        return new ErrorResponse(
                message,
                LocalDateTime.now(),
                BAD_REQUEST.value(),
                BAD_REQUEST.getReasonPhrase(),
                errors);
    }

    public static ErrorResponse from(BaseException exception) {
        return new ErrorResponse(
                exception.getMessage(),
                exception.getTimestamp(),
                exception.getStatus().value(),
                exception.getStatus().getReasonPhrase(),
                null);
    }

    public static ErrorResponse from(String message) {
        return new ErrorResponse(
                message,
                LocalDateTime.now(),
                INTERNAL_SERVER_ERROR.value(),
                INTERNAL_SERVER_ERROR.getReasonPhrase(),
                null);
    }
}
