package admin.adminsiteserver.promotion.ui.request;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PromotionCursor {
    private Long promotionId;

    public Long promotionId() {
        if (promotionId == null) {
            return Long.MAX_VALUE;
        }
        return promotionId;
    }
}
