package admin.adminsiteserver.member.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    <S extends Member> List<S> saveAll(Iterable<S> entities);

    Optional<Member> findById(Long id);

    Optional<Member> findByIdAndAndDeletedFalse(Long id);

    Optional<Member> findByEmail(String email);

    Page<Member> findAllByDeletedFalse(Pageable pageable);

    boolean existsByStudentNumberAndDeletedFalse(String studentNumber);

    boolean existsByEmailOrStudentNumberAndDeletedFalse(String email, String studentNumber);
}
