package admin.adminsiteserver.member.member.ui.dto;

import admin.adminsiteserver.member.member.domain.Member;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class SignUpRequest {

    private String userId;
    private String password;
    private String email;
    private String name;
    private String studentNumber;
    private String phoneNumber;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .userId(userId)
                .password(passwordEncoder.encode(password))
                .email(email)
                .name(name)
                .studentNumber(studentNumber)
                .phoneNumber(phoneNumber)
                .build();
    }
}
