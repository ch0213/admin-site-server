package admin.adminsiteserver.common.aws.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static admin.adminsiteserver.common.aws.exception.AwsExceptionType.CONVERT_FILE_EXCEPTION;
import static admin.adminsiteserver.member.member.exception.MemberExceptionType.NOT_EXIST_USER;

@Getter
public class ConvertFileException extends BaseException {
    public ConvertFileException() {
        super(CONVERT_FILE_EXCEPTION.getMessage(), LocalDateTime.now(), CONVERT_FILE_EXCEPTION.getStatus());
    }
}
