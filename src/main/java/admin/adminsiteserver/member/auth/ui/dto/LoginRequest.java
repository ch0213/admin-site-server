package admin.adminsiteserver.member.auth.ui.dto;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String email;
    private String password;
}
