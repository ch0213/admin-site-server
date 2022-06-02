package admin.adminsiteserver.aws.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static admin.adminsiteserver.aws.exception.AwsExceptionType.CONVERT_FILE_EXCEPTION;

@Getter
public class ConvertFileException extends BaseException {
    public ConvertFileException() {
        super(CONVERT_FILE_EXCEPTION.getMessage(), LocalDateTime.now(), CONVERT_FILE_EXCEPTION.getStatus());
    }
}
