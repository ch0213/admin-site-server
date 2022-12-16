package admin.adminsiteserver.promotion.ui.request;

import admin.adminsiteserver.common.domain.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class PromotionRequest {
    @NotBlank(message = "역할을 입력해주세요.")
    private String role;

    public PromotionRequest(String role) {
        this.role = role;
    }

    public RoleType role() {
        return RoleType.from(role);
    }
}
