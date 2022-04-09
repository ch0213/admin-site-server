package admin.adminsiteserver.levelup.ui.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class LevelUpProcessRequest {
    private List<Long> levelUpIds;
}
