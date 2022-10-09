package admin.adminsiteserver.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionController {
    public static final String VALID_ERROR_MESSAGE = "요청 양식을 확인해주세요.";

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> baseExceptionHandle(BaseException exception) {
        return new ResponseEntity<>(ErrorResponse.from(exception), exception.getStatus());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException exception) {
        Map<String, String> errors = extractErrorMessages(exception);
        return new ResponseEntity<>(ErrorResponse.of(VALID_ERROR_MESSAGE, errors), BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        return new ResponseEntity<>(ErrorResponse.from("예상치 못한 에러입니다."), INTERNAL_SERVER_ERROR);
    }

    private Map<String, String> extractErrorMessages(BindException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult()
                .getAllErrors()
                .forEach(error -> errors.put(((FieldError) error).getField(), error.getDefaultMessage()));
        return errors;
    }
}
