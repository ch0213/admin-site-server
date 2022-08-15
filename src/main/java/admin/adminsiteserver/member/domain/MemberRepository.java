package admin.adminsiteserver.member.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Page<Member> findAllByDeletedFalse(Pageable pageable);

    boolean existsByStudentNumberAndDeletedFalse(String studentNumber);

    boolean existsByEmailOrStudentNumberAndDeletedFalse(String email, String studentNumber);
}
