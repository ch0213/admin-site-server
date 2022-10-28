package admin.adminsiteserver.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

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
        Map<String, String> errors = errors(exception);
        return new ResponseEntity<>(ErrorResponse.of(VALID_ERROR_MESSAGE, errors), BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error("Internal server error", exception);
        return new ResponseEntity<>(ErrorResponse.from("예상치 못한 에러입니다. 관리자에게 문의해주세요."), INTERNAL_SERVER_ERROR);
    }

    private Map<String, String> errors(BindException exception) {
        return exception.getBindingResult()
                .getAllErrors()
                .stream()
                .collect(Collectors.toMap(this::field, this::message));
    }

    private String field(ObjectError error) {
        return ((FieldError) error).getField();
    }

    private String message(ObjectError error) {
        return error.getDefaultMessage();
    }
}
