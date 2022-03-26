package admin.adminsiteserver.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionController {

    public static final String VALID_ERROR_MESSAGE = "요청 양식을 확인해주세요.";

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> userExHandle(BaseException exception) {
        return new ResponseEntity<>(ErrorResponse.from(exception), exception.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors()
                .forEach(error -> errors.put(((FieldError) error).getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(ErrorResponse.of(VALID_ERROR_MESSAGE, errors), BAD_REQUEST);
    }
}
