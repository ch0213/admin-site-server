package admin.adminsiteserver.member.application;

import admin.adminsiteserver.member.domain.MemberEvent;
import admin.adminsiteserver.member.domain.MemberEventHistory;
import admin.adminsiteserver.member.domain.MemberEventHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;

@Component
@RequiredArgsConstructor
public class MemberEventListener {
    private final MemberEventHistoryRepository memberEventHistoryRepository;

    @Transactional
    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void handle(MemberEvent event) {
        MemberEventHistory history = event.history();
        memberEventHistoryRepository.save(history);
    }
}
