package admin.adminsiteserver.promotion.application;

import admin.adminsiteserver.common.domain.RoleType;
import admin.adminsiteserver.promotion.domain.Author;
import admin.adminsiteserver.promotion.exception.PromotionAlreadyExistException;
import admin.adminsiteserver.promotion.ui.request.PromotionFindRequest;
import admin.adminsiteserver.promotion.ui.response.PromotionResponse;
import admin.adminsiteserver.promotion.domain.Promotion;
import admin.adminsiteserver.promotion.domain.PromotionRepository;
import admin.adminsiteserver.promotion.exception.PromotionNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static admin.adminsiteserver.promotion.domain.PromotionStatus.WAITING;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PromotionService {
    private static final String APPROVED_LOG_FORM = "Member({})'s promotion request({}) approved.";
    private static final String REJECTED_LOG_FORM = "Member({})'s promotion request({}) rejected.";

    private final PromotionRepository promotionRepository;

    @Transactional
    public Long apply(RoleType role, Author author) {
        if (promotionRepository.existsByAuthorAndStatus(author, WAITING)) {
            throw new PromotionAlreadyExistException();
        }
        Promotion promotion = promotionRepository.save(new Promotion(author, role));
        return promotion.getId();
    }

    @Transactional
    public void update(Long promotionId, RoleType role, Author author) {
        Promotion promotion = findById(promotionId);
        promotion.update(author, role);
    }

    @Transactional
    public void cancel(Long promotionId, Author author) {
        Promotion promotion = findById(promotionId);
        promotion.cancel(author);
    }

    @Transactional
    public void approve(Long promotionId) {
        Promotion promotion = findById(promotionId);
        promotion.approve();
        promotionRepository.save(promotion);
        log(APPROVED_LOG_FORM, promotion);
    }

    @Transactional
    public void approveAll(List<Long> promotionIds) {
        List<Promotion> promotions = promotionRepository.findAllByIdInAndStatus(promotionIds, WAITING);
        promotions.forEach(Promotion::approve);
        promotionRepository.saveAll(promotions);
        promotions.forEach(promotion -> log(APPROVED_LOG_FORM, promotion));
    }

    @Transactional
    public void reject(Long promotionId) {
        Promotion promotion = findById(promotionId);
        promotion.reject();
        promotionRepository.save(promotion);
        log(APPROVED_LOG_FORM, promotion);
    }

    @Transactional
    public void rejectAll(List<Long> promotionIds) {
        List<Promotion> promotions = promotionRepository.findAllByIdInAndStatus(promotionIds, WAITING);
        promotions.forEach(Promotion::reject);
        promotionRepository.saveAll(promotions);
        promotions.forEach(promotion -> log(REJECTED_LOG_FORM, promotion));
    }

    public List<PromotionResponse> findAll(PromotionFindRequest request, Pageable pageable) {
        if (request.noneParameter()) {
            return toResponses(promotionRepository.findAll(request.promotionId(), pageable));
        }
        return toResponses(promotionRepository.findAllByStatus(request.promotionId(), request.status(), pageable));
    }

    public List<PromotionResponse> myPromotions(PromotionFindRequest request, Author author, Pageable pageable) {
        if (request.noneParameter()) {
            List<Promotion> promotions = promotionRepository.findAllByAuthor(request.promotionId(), author, pageable);
            return toResponses(promotions);
        }
        List<Promotion> promotions = promotionRepository.findAllByAuthorAndStatus(
                request.promotionId(),
                author,
                request.status(),
                pageable
        );
        return toResponses(promotions);
    }

    private List<PromotionResponse> toResponses(List<Promotion> promotions) {
        return promotions.stream()
                .map(PromotionResponse::from)
                .collect(Collectors.toList());
    }

    private Promotion findById(Long promotionId) {
        return promotionRepository.findByIdAndStatus(promotionId, WAITING)
                .orElseThrow(PromotionNotFoundException::new);
    }

    private void log(String format, Promotion promotion) {
        log.info(format, promotion.getAuthor(), promotion.getRole());
    }
}
