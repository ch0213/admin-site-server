package admin.adminsiteserver.member.infra;

import admin.adminsiteserver.member.domain.Member;
import admin.adminsiteserver.member.domain.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends MemberRepository, JpaRepository<Member, Long> {
}
