package admin.adminsiteserver.announcement.exception;

import admin.adminsiteserver.common.exception.BaseException;

import java.time.LocalDateTime;

import static admin.adminsiteserver.announcement.exception.AnnouncementExceptionType.UNAUTHORIZED_FOR_ANNOUNCEMENT;

public class UnauthorizedForAnnouncementException extends BaseException {
    public UnauthorizedForAnnouncementException() {
        super(UNAUTHORIZED_FOR_ANNOUNCEMENT.getMessage(), LocalDateTime.now(), UNAUTHORIZED_FOR_ANNOUNCEMENT.getStatus());
    }
}
