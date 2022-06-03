package admin.adminsiteserver.member.member.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static admin.adminsiteserver.member.member.exception.MemberExceptionType.ALREADY_EXIST_STUDENT_NUMBER;

@Getter
public class AlreadyExistStudentNumberException extends BaseException {
    public AlreadyExistStudentNumberException() {
        super(ALREADY_EXIST_STUDENT_NUMBER.getMessage(), LocalDateTime.now(), ALREADY_EXIST_STUDENT_NUMBER.getStatus());
    }
}
