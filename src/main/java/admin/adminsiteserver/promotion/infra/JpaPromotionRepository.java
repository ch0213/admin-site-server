package admin.adminsiteserver.promotion.infra;

import admin.adminsiteserver.promotion.domain.Author;
import admin.adminsiteserver.promotion.domain.Promotion;
import admin.adminsiteserver.promotion.domain.PromotionRepository;
import admin.adminsiteserver.promotion.domain.PromotionStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaPromotionRepository extends PromotionRepository, JpaRepository<Promotion, Long> {
    @Query("SELECT p FROM Promotion p WHERE p.id <= ?1")
    List<Promotion> findAll(Long id, Pageable pageable);

    @Query("SELECT p FROM Promotion p WHERE p.id <= ?1 AND p.status = ?2")
    List<Promotion> findAllByStatus(Long id, PromotionStatus status, Pageable pageable);

    @Query("SELECT p FROM Promotion p WHERE p.id <= ?1 AND p.author = ?2")
    List<Promotion> findAllByAuthor(Long id, Author author, Pageable pageable);

    @Query("SELECT p FROM Promotion p WHERE p.id <= ?1 AND p.author = ?2 AND p.status = ?3")
    List<Promotion> findAllByAuthorAndStatus(Long id, Author author, PromotionStatus status, Pageable pageable);
}
