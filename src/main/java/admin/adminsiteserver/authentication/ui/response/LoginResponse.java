package admin.adminsiteserver.authentication.ui.response;

import admin.adminsiteserver.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String email;
    private String name;
    private String studentNumber;
    private String phoneNumber;
    private String role;
    private TokenResponse tokens;

    public static LoginResponse of(Member member, TokenResponse tokens) {
        return new LoginResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getStudentNumber(),
                member.getPhoneNumber(), 
                member.getRole().getDescription(),
                tokens);
    }
}
