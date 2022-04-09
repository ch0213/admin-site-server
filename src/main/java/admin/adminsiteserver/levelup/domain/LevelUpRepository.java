package admin.adminsiteserver.levelup.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LevelUpRepository extends JpaRepository<LevelUp, Long> {
    List<LevelUp> findByProcessedIsFalse();
    Optional<LevelUp> findByUserId(String userId);
    List<LevelUp> findAllById(Iterable<Long> longs);
}
