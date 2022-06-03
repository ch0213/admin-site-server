package admin.adminsiteserver.member.auth.application.dto;

import admin.adminsiteserver.member.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private Long id;
    private String email;
    private String name;
    private String studentNumber;
    private String phoneNumber;
    private String role;
    private JwtTokenDto tokens;

    public static LoginResponse of(Member member, JwtTokenDto tokens) {
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
