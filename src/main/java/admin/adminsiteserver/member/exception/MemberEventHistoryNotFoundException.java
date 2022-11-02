package admin.adminsiteserver.member.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class MemberEventHistoryNotFoundException extends BaseException {
    public MemberEventHistoryNotFoundException() {
        super("회원 이벤트 기록을 찾을 수 없습니다.", LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
