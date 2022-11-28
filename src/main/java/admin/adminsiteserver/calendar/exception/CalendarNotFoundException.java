package admin.adminsiteserver.calendar.exception;

import admin.adminsiteserver.common.exception.BaseException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class CalendarNotFoundException extends BaseException {
    public CalendarNotFoundException() {
        super("일정을 찾을 수 없습니다.", LocalDateTime.now(), HttpStatus.BAD_REQUEST);
    }
}
