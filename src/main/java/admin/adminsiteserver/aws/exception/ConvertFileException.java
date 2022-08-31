package admin.adminsiteserver.aws.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
public class ConvertFileException extends BaseException {
    public ConvertFileException() {
        super("파일을 변환할 수 없습니다.", LocalDateTime.now(), INTERNAL_SERVER_ERROR);
    }
}
