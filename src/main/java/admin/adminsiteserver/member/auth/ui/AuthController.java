package admin.adminsiteserver.member.auth.ui;

import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.member.auth.application.AuthService;
import admin.adminsiteserver.member.auth.application.dto.LoginResponse;
import admin.adminsiteserver.member.auth.ui.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static admin.adminsiteserver.member.auth.ui.AuthResponseMessage.LOGIN_SUCCESS;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public CommonResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.login(request);
        return CommonResponse.of(loginResponse, LOGIN_SUCCESS.getMessage());
    }
}
