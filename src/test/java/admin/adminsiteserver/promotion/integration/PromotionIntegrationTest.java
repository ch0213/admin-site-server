package admin.adminsiteserver.promotion.integration;

import admin.adminsiteserver.promotion.application.PromotionService;
import admin.adminsiteserver.promotion.domain.Author;
import admin.adminsiteserver.promotion.domain.PromotionEvent;
import admin.adminsiteserver.utils.DatabaseCleanup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.util.List;
import java.util.stream.Collectors;

import static admin.adminsiteserver.common.domain.RoleType.MEMBER;
import static admin.adminsiteserver.common.domain.RoleType.OFFICER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@ActiveProfiles("test")
@RecordApplicationEvents
class PromotionIntegrationTest {
    @Autowired
    private PromotionService promotionService;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private ApplicationEvents events;

    private Long promotionId1;

    private Long promotionId2;

    @BeforeEach
    void save() {
        databaseCleanup.execute();

        promotionId1 = promotionService.apply(OFFICER, new Author(1L, "신짱구", "202012345", MEMBER));
        promotionId2 = promotionService.apply(OFFICER, new Author(2L, "김철수", "202054321", MEMBER));
    }

    @Test
    void 승진_신청을_수락하면_이벤트가_발생한다() {
        promotionService.approve(promotionId1);

        assertAll(
                () -> assertThat(events.stream(PromotionEvent.class).count()).isOne(),
                () -> assertThat(firstEvent().getPromotionId()).isEqualTo(promotionId1),
                () -> assertThat(firstEvent().getStatus().name()).isEqualTo("APPROVE")
        );
    }

    @Test
    void 승진_신청_목록을_수락하면_이벤트가_발생한다() {
        promotionService.approveAll(List.of(promotionId1, promotionId2));

        assertAll(
                () -> assertThat(events.stream(PromotionEvent.class).count()).isEqualTo(2),
                () -> assertThat(events.stream(PromotionEvent.class).map(PromotionEvent::getPromotionId))
                        .containsExactly(promotionId1, promotionId2),
                () -> assertThat(events.stream(PromotionEvent.class).map(it -> it.getStatus().name()))
                        .containsExactly("APPROVE", "APPROVE")
        );
    }

    @Test
    void 승진_신청을_거절하면_이벤트가_발생한다() {
        promotionService.reject(promotionId1);

        assertAll(
                () -> assertThat(events.stream(PromotionEvent.class).count()).isOne(),
                () -> assertThat(firstEvent().getPromotionId()).isEqualTo(promotionId1),
                () -> assertThat(firstEvent().getStatus().name()).isEqualTo("REJECT")
        );
    }

    @Test
    void 승진_신청_목록을_거절하면_이벤트가_발생한다() {
        promotionService.rejectAll(List.of(promotionId1, promotionId2));

        assertAll(
                () -> assertThat(events.stream(PromotionEvent.class).count()).isEqualTo(2),
                () -> assertThat(events.stream(PromotionEvent.class).map(PromotionEvent::getPromotionId))
                        .containsExactly(promotionId1, promotionId2),
                () -> assertThat(events.stream(PromotionEvent.class).map(it -> it.getStatus().name()))
                        .containsExactly("REJECT", "REJECT")
        );
    }

    private PromotionEvent firstEvent() {
        return events.stream(PromotionEvent.class).collect(Collectors.toList())
                .get(0);
    }
}
