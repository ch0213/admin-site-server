package admin.adminsiteserver.member.auth.application.dto;

import admin.adminsiteserver.member.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String userId;
    private String email;
    private String name;
    private String role;
    private JwtTokenDto tokens;

    public static LoginResponse of(Member member, JwtTokenDto tokens) {
        return new LoginResponse(
                member.getUserId(),
                member.getEmail(),
                member.getName(),
                member.getRole().getRole(),
                tokens);
    }
}
