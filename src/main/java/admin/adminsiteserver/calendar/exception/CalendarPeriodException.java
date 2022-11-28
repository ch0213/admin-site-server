package admin.adminsiteserver.calendar.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class CalendarPeriodException extends BaseException {
    public CalendarPeriodException() {
        this("종료일은 시작일 이후여야 합니다.");
    }

    public CalendarPeriodException(String message) {
        super(message, LocalDateTime.now(), HttpStatus.BAD_REQUEST);
    }
}
