package admin.adminsiteserver.announcement.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static admin.adminsiteserver.announcement.exception.AnnouncementExceptionType.NOT_EXIST_ANNOUNCEMENT_COMMENT;

@Getter
public class NotExistAnnouncementCommentException extends BaseException {
    public NotExistAnnouncementCommentException() {
        super(NOT_EXIST_ANNOUNCEMENT_COMMENT.getMessage(), LocalDateTime.now(), NOT_EXIST_ANNOUNCEMENT_COMMENT.getStatus());
    }
}
