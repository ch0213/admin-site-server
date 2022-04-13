package admin.adminsiteserver.announcement.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static admin.adminsiteserver.announcement.exception.AnnouncementExceptionType.UNAUTHORIZED_FOR_ANNOUNCEMENT_COMMENT;

@Getter
public class UnauthorizedForAnnouncementCommentException extends BaseException {
    public UnauthorizedForAnnouncementCommentException() {
        super(UNAUTHORIZED_FOR_ANNOUNCEMENT_COMMENT.getMessage(), LocalDateTime.now(), UNAUTHORIZED_FOR_ANNOUNCEMENT_COMMENT.getStatus());
    }
}
