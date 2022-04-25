package admin.adminsiteserver.levelup.ui.dto;

import admin.adminsiteserver.levelup.domain.LevelUp;
import admin.adminsiteserver.levelup.exception.NotExistRoleException;
import admin.adminsiteserver.member.member.domain.Member;
import admin.adminsiteserver.member.member.domain.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LevelUpRequest {
    @NotBlank(message = "역할을 입력해주세요.")
    private String role;

    public LevelUp from(Member member) {
        return new LevelUp(member.getEmail(), findRoleType(), false, member);
    }

    private String findRoleType() {
        return Arrays.stream(RoleType.values()).sequential()
                .filter(roleType -> roleType.getDescription().equals(role))
                .findAny()
                .orElseThrow(NotExistRoleException::new)
                .getDescription();
    }
}
