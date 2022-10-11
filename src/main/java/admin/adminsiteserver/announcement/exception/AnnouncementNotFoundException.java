package admin.adminsiteserver.announcement.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
public class AnnouncementNotFoundException extends BaseException {
    public AnnouncementNotFoundException() {
        super("존재하지 않는 공지사항입니다.", LocalDateTime.now(), BAD_REQUEST);
    }
}
