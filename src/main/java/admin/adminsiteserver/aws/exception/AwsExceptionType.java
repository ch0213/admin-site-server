package admin.adminsiteserver.aws.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@RequiredArgsConstructor
public enum AwsExceptionType {
    CONVERT_FILE_EXCEPTION("파일을 변환할 수 없습니다.", INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;
}
