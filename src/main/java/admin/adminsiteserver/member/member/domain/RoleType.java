package admin.adminsiteserver.member.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    ADMIN("ROLE_ADMIN", "관리자"),
    PRESIDENT("ROLE_PRESIDENT", "회장"),
    OFFICER("ROLE_OFFICER", "임원"),
    MEMBER("ROLE_MEMBER", "회원"),
    GUEST("ROLE_GUEST", "비회원");

    private String role;
    private String description;
}
