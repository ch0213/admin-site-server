package admin.adminsiteserver.member.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
public class NonExistentMemberException extends BaseException {
    public NonExistentMemberException() {
        super("존재하지 않는 유저입니다.", LocalDateTime.now(), BAD_REQUEST);
    }
}
