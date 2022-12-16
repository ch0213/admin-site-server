package admin.adminsiteserver.promotion.domain;

import admin.adminsiteserver.common.domain.RoleType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PromotionEvent {
    private Long promotionId;

    private Long memberId;

    private PromotionStatus status;

    private RoleType role;

    private LocalDateTime eventDateTime;

    public PromotionEvent(Long promotionId, Long memberId, PromotionStatus status, RoleType role) {
        this.promotionId = promotionId;
        this.memberId = memberId;
        this.status = status;
        this.role = role;
        this.eventDateTime = LocalDateTime.now();
    }
}
