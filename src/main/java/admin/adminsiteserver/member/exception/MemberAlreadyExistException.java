package admin.adminsiteserver.member.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
public class MemberAlreadyExistException extends BaseException {
    public MemberAlreadyExistException() {
        super("이메일 또는 학번이 이미 존재합니다.", LocalDateTime.now(), BAD_REQUEST);
    }
}
