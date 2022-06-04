package admin.adminsiteserver.member.member.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Page<Member> findAllByDeletedFalse(Pageable pageable);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByEmailOrStudentNumber(String email, String studentNumber);
    Optional<Member> findByStudentNumberAndDeletedFalse(String studentNumber);
}
