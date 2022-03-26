package admin.adminsiteserver.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private String message;
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private Map<String, String> errors;

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
}
