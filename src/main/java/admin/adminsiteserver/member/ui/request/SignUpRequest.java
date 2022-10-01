package admin.adminsiteserver.member.ui.request;

import admin.adminsiteserver.member.domain.Member;
import admin.adminsiteserver.member.domain.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class SignUpRequest {

    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "학번을 입력해주세요.")
    private String studentNumber;

    @NotBlank(message = "연락처를 입력해주세요.")
    private String phoneNumber;

    private MultipartFile image;

    public boolean hasImage() {
        return image != null;
    }

    public Member toMember(String password) {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .studentNumber(studentNumber)
                .phoneNumber(phoneNumber)
                .role(RoleType.MEMBER)
                .build();
    }
}
