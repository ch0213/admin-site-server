package admin.adminsiteserver.member.ui.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UpdateMemberRequest {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "학번을 입력해주세요.")
    private String studentNumber;

    @NotBlank(message = "연락처를 입력해주세요.")
    private String phoneNumber;
}
