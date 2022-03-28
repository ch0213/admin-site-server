package admin.adminsiteserver.member.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    PRESIDENT("PRESIDENT", "회장"),
    OFFICER("OFFICER", "임원"),
    MEMBER("MEMBER", "회원");

    private String role;
    private String description;
}
