package admin.adminsiteserver.promotion.domain;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PromotionRepository {
    Promotion save(Promotion promotion);

    <S extends Promotion> Iterable<S> saveAll(Iterable<S> entities);

    Optional<Promotion> findByIdAndStatus(Long id, PromotionStatus status);

    List<Promotion> findAllByIdInAndStatus(List<Long> ids, PromotionStatus status);

    List<Promotion> findAll(Long id, Pageable pageable);

    List<Promotion> findAllByStatus(Long id, PromotionStatus status, Pageable pageable);

    List<Promotion> findAllByAuthor(Long id, Author author, Pageable pageable);

    List<Promotion> findAllByAuthorAndStatus(Long id, Author author, PromotionStatus status, Pageable pageable);

    boolean existsByAuthorAndStatus(Author author, PromotionStatus status);
}
