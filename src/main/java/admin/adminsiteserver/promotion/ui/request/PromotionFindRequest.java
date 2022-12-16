package admin.adminsiteserver.promotion.ui.request;

import admin.adminsiteserver.promotion.domain.PromotionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PromotionFindRequest {
    private Long promotionId;

    private String type;

    public Long promotionId() {
        if (promotionId == null) {
            return Long.MAX_VALUE;
        }
        return promotionId;
    }

    public PromotionStatus status() {
        return PromotionStatus.from(type);
    }

    public boolean noneParameter() {
        return type == null;
    }
}
