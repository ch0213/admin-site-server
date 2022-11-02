package admin.adminsiteserver.announcement.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class CommentLengthException extends BaseException {
    public CommentLengthException() {
        super("공지사항 댓글의 최대 길이를 초과했습니다.", LocalDateTime.now(), HttpStatus.BAD_REQUEST);
    }
}
