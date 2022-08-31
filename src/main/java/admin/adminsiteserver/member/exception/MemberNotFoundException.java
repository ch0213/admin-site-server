package admin.adminsiteserver.member.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
public class MemberNotFoundException extends BaseException {
    public MemberNotFoundException() {
        super("존재하지 않는 유저입니다.", LocalDateTime.now(), BAD_REQUEST);
    }
}
