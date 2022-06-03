package admin.adminsiteserver.member.member.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class UpdatePasswordRequest {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
