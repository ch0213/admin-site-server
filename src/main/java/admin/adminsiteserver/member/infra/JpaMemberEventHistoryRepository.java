package admin.adminsiteserver.member.infra;

import admin.adminsiteserver.member.domain.MemberEventHistory;
import admin.adminsiteserver.member.domain.MemberEventHistoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberEventHistoryRepository extends MemberEventHistoryRepository, JpaRepository<MemberEventHistory, Long> {
}
