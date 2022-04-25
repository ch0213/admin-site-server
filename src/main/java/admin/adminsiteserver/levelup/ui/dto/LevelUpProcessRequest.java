package admin.adminsiteserver.levelup.ui.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class LevelUpProcessRequest {
    @NotNull(message = "권한 신청 아이디를 입력해주세요.")
    private List<Long> levelUpIds;
}
