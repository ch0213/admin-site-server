package admin.adminsiteserver.qna.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    Optional<Qna> findById(Long id);
    Page<Qna> findAll(Pageable pageable);
}
