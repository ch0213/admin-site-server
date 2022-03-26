package admin.adminsiteserver.member.member.ui.dto;

import admin.adminsiteserver.member.member.domain.Member;
import lombok.Getter;

@Getter
public class SignUpRequest {

    private String userId;
    private String password;
    private String email;
    private String name;
    private String studentNumber;
    private String phoneNumber;

    public void encryptPassword(String encryptedPassword) {
        password = encryptedPassword;
    }

    public Member toMember() {
        return Member.builder()
                .userId(userId)
                .password(password)
                .email(email)
                .name(name)
                .studentNumber(studentNumber)
                .phoneNumber(phoneNumber)
                .build();
    }
}
