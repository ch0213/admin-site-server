package admin.adminsiteserver.promotion.exception;

import admin.adminsiteserver.common.exception.BaseException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class PromotionAlreadyExistException extends BaseException {
    public PromotionAlreadyExistException() {
        super("이미 신청했습니다.", LocalDateTime.now(), HttpStatus.BAD_REQUEST);
    }
}
