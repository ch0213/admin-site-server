package admin.adminsiteserver.member.application;

import admin.adminsiteserver.member.domain.Member;
import admin.adminsiteserver.member.domain.MemberEvent;
import admin.adminsiteserver.member.domain.MemberEventHistory;
import admin.adminsiteserver.member.domain.MemberEventHistoryRepository;
import admin.adminsiteserver.member.domain.MemberRepository;
import admin.adminsiteserver.member.exception.MemberNotFoundException;
import admin.adminsiteserver.promotion.domain.PromotionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;

@Component
@RequiredArgsConstructor
public class MemberEventListener {
    private final MemberEventHistoryRepository memberEventHistoryRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void handle(MemberEvent event) {
        MemberEventHistory history = event.history();
        memberEventHistoryRepository.save(history);
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void promotionEvent(PromotionEvent event) {
        if (event.approved()) {
            Member member = findById(event.getMemberId());
            member.updateRole(event.getRole());
        }
    }

    private Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }
}
