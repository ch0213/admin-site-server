package admin.adminsiteserver.promotion.exception;

import admin.adminsiteserver.common.exception.BaseException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class PromotionStatusNotFoundException extends BaseException {
    public PromotionStatusNotFoundException() {
        super("존재하지 않는 승진 신청 상태입니다.", LocalDateTime.now(), HttpStatus.BAD_REQUEST);
    }
}
