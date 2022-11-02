package admin.adminsiteserver.member.domain;

import java.util.List;
import java.util.Optional;

public interface MemberEventHistoryRepository {
    MemberEventHistory save(MemberEventHistory memberEventHistory);

    Optional<MemberEventHistory> findByMemberId(Long memberId);

    List<MemberEventHistory> findAllByProcessedFalse();
}
