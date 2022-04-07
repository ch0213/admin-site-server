package admin.adminsiteserver.calendar.exception;

import admin.adminsiteserver.common.exception.BaseException;

import java.time.LocalDateTime;

import static admin.adminsiteserver.calendar.exception.CalendarExceptionType.NOT_EXIST_CALENDAR;

public class NotExistCalendarException extends BaseException {
    public NotExistCalendarException() {
        super(NOT_EXIST_CALENDAR.getMessage(), LocalDateTime.now(), NOT_EXIST_CALENDAR.getStatus());
    }
}
