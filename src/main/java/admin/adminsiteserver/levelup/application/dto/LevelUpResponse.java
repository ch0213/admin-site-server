package admin.adminsiteserver.levelup.application.dto;

import admin.adminsiteserver.levelup.domain.LevelUp;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class LevelUpResponse {

    private Long id;
    private String userId;
    private String name;
    private String registerRoleType;
    private LocalDateTime registerAt;

    public static LevelUpResponse from(LevelUp levelUp) {
        return new LevelUpResponse(
                levelUp.getId(),
                levelUp.getUserEmail(),
                levelUp.getMember().getName(),
                levelUp.getRole().getDescription(),
                levelUp.getCreatedAt()
        );
    }
}
