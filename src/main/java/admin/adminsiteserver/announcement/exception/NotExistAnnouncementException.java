package admin.adminsiteserver.post.announcement.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static admin.adminsiteserver.post.announcement.exception.AnnouncementExceptionType.NOT_EXIST_ANNOUNCEMENT;

@Getter
public class NotExistAnnouncementException extends BaseException {
    public NotExistAnnouncementException() {
        super(NOT_EXIST_ANNOUNCEMENT.getMessage(), LocalDateTime.now(), NOT_EXIST_ANNOUNCEMENT.getStatus());
    }
}
