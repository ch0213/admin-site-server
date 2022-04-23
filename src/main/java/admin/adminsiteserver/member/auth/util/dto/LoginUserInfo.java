package admin.adminsiteserver.member.auth.util.dto;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public class LoginUserInfo {
    private static final String USER_ID = "userId";
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String ROLE = "role";

    private String userId;
    private String email;
    private String name;
    private String role;

    public boolean isNotEqualUser(String userId) {
        return !this.userId.equals(userId);
    }

    public static LoginUserInfo from(Claims claims) {
        return new LoginUserInfo(
                (String) claims.get(USER_ID),
                (String) claims.get(EMAIL),
                (String) claims.get(NAME),
                (String) claims.get(ROLE)
        );
    }
}
