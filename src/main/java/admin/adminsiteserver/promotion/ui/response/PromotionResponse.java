package admin.adminsiteserver.promotion.ui.response;

import admin.adminsiteserver.promotion.domain.Promotion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionResponse {
    private Long id;
    private Long memberId;
    private String name;
    private String studentNumber;
    private String currentRole;
    private String registerRoleType;
    private String status;
    private LocalDateTime registerAt;

    public static PromotionResponse from(Promotion promotion) {
        return new PromotionResponse(
                promotion.getId(),
                promotion.getAuthor().getMemberId(),
                promotion.getAuthor().getName(),
                promotion.getAuthor().getStudentNumber(),
                promotion.getAuthor().getRoleType().getDescription(),
                promotion.getRole().getDescription(),
                promotion.getStatus().name(),
                promotion.getCreatedAt()
        );
    }
}
