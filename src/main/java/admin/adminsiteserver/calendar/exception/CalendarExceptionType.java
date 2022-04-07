package admin.adminsiteserver.calendar.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum CalendarExceptionType {
    NOT_EXIST_CALENDAR("존재하지 않는 일정입니다.", BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
