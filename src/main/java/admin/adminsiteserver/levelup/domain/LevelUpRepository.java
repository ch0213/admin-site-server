package admin.adminsiteserver.levelup.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LevelUpRepository extends JpaRepository<LevelUp, Long> {
    Optional<LevelUp> findByIdAndProcessedIsFalse(Long id);
    List<LevelUp> findAllByProcessedIsFalse();
    Optional<LevelUp> findByUserEmailAndProcessedIsFalse(String userId);
    List<LevelUp> findAllById(Iterable<Long> longs);
}
