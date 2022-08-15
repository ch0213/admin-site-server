package admin.adminsiteserver.member.domain;

import admin.adminsiteserver.levelup.exception.NotExistRoleException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    ADMIN("ROLE_ADMIN", "관리자"),
    PRESIDENT("ROLE_PRESIDENT", "회장"),
    OFFICER("ROLE_OFFICER", "임원"),
    MEMBER("ROLE_MEMBER", "회원"),
    GUEST("ROLE_GUEST", "비회원");

    private final String role;
    private final String description;

    public static RoleType matchRole(String newRole) {
        return Arrays.stream(RoleType.values())
                .filter(roleType -> roleType.getDescription().equals(newRole))
                .findAny()
                .orElseThrow(NotExistRoleException::new);
    }
}
