package admin.adminsiteserver.promotion.exception;

import admin.adminsiteserver.common.exception.BaseException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class PromotionNotFoundException extends BaseException {
    public PromotionNotFoundException() {
        super("존재하지 않는 신청입니다.", LocalDateTime.now(), HttpStatus.BAD_REQUEST);
    }
}
