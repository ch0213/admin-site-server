package admin.adminsiteserver.promotion.domain;

import admin.adminsiteserver.promotion.exception.PromotionStatusNotFoundException;

import java.util.Arrays;

public enum PromotionStatus {
    WAITING, CANCEL, APPROVE, REJECT;

    public static PromotionStatus from(String type) {
        return Arrays.stream(PromotionStatus.values())
                .filter(it -> it.matches(type))
                .findAny()
                .orElseThrow(PromotionStatusNotFoundException::new);
    }

    private boolean matches(String type) {
        return this.name().equals(type);
    }
}
