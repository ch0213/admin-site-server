package admin.adminsiteserver.promotion.ui;

import admin.adminsiteserver.authentication.domain.LoginMember;
import admin.adminsiteserver.promotion.application.PromotionService;
import admin.adminsiteserver.promotion.domain.Author;
import admin.adminsiteserver.promotion.ui.request.PromotionFindRequest;
import admin.adminsiteserver.promotion.ui.response.PromotionResponse;
import admin.adminsiteserver.promotion.ui.request.PromotionProcessRequest;
import admin.adminsiteserver.promotion.ui.request.PromotionRequest;
import admin.adminsiteserver.authentication.ui.AuthenticationPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/promotions")
public class PromotionController {
    private final PromotionService promotionService;

    @PostMapping
    public ResponseEntity<Void> register(@Valid @RequestBody PromotionRequest request,
                                         @AuthenticationPrincipal LoginMember loginMember) {
        Long promotionId = promotionService.apply(request.role(), loginMember.toAuthor(Author::new));
        return ResponseEntity.created(URI.create("/promotions/" + promotionId)).build();
    }

    @PutMapping("/{promotionId}")
    public void update(@PathVariable Long promotionId,
                       @Valid @RequestBody PromotionRequest request,
                       @AuthenticationPrincipal LoginMember loginMember) {
        promotionService.update(promotionId, request.role(), loginMember.toAuthor(Author::new));
    }

    @DeleteMapping("/{promotionId}")
    public ResponseEntity<Void> delete(@PathVariable Long promotionId,
                                       @AuthenticationPrincipal LoginMember loginMember) {
        promotionService.cancel(promotionId, loginMember.toAuthor(Author::new));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{promotionId}/approve")
    public void approve(@PathVariable Long promotionId) {
        promotionService.approve(promotionId);
    }

    @PostMapping("/approve")
    public void approveAll(@Valid @RequestBody PromotionProcessRequest request) {
        promotionService.approveAll(request.getPromotionIds());
    }

    @PostMapping("/{promotionId}/reject")
    public void reject(@PathVariable Long promotionId) {
        promotionService.reject(promotionId);
    }

    @PostMapping("/reject")
    public void rejectAll(@Valid @RequestBody PromotionProcessRequest request) {
        promotionService.rejectAll(request.getPromotionIds());
    }

    @GetMapping
    public List<PromotionResponse> findAll(PromotionFindRequest request, Pageable pageable) {
        return promotionService.findAll(request, pageable);
    }

    @GetMapping("/me")
    public List<PromotionResponse> myPromotions(@AuthenticationPrincipal LoginMember loginMember,
                                                PromotionFindRequest request,
                                                Pageable pageable) {
        return promotionService.myPromotions(request, loginMember.toAuthor(Author::new), pageable);
    }
}
