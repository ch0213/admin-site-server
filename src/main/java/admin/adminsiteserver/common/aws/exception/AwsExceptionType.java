package admin.adminsiteserver.common.aws.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@AllArgsConstructor
public enum AwsExceptionType {
    CONVERT_FILE_EXCEPTION("파일을 변환할 수 없습니다.", INTERNAL_SERVER_ERROR);

    private final String message;
    private HttpStatus status;
}
