package admin.adminsiteserver.promotion.exception;

import admin.adminsiteserver.common.exception.BaseException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class WrongPromotionException extends BaseException {
    public WrongPromotionException() {
        super("현재 직책보다 같거나 낮은 직책으로 신청할 수 없습니다.", LocalDateTime.now(), HttpStatus.BAD_REQUEST);
    }
}
