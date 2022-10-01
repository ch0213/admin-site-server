package admin.adminsiteserver.member.ui.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UpdatePasswordRequest {
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
