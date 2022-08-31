package admin.adminsiteserver.authentication.ui;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public class LoginUserInfo {
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String ROLE = "role";

    private String email;
    private String name;
    private String role;

    public boolean isNotEqualUser(String userId) {
        return !this.email.equals(userId);
    }

    public static LoginUserInfo from(Claims claims) {
        return new LoginUserInfo(
                (String) claims.get(EMAIL),
                (String) claims.get(NAME),
                (String) claims.get(ROLE)
        );
    }
}
