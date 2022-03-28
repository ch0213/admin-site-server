package admin.adminsiteserver.member.member.ui;

import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.member.member.application.MemberService;
import admin.adminsiteserver.member.member.application.dto.MemberDto;
import admin.adminsiteserver.member.member.ui.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static admin.adminsiteserver.member.member.ui.MemberResponseMessage.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public CommonResponse<MemberDto> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return CommonResponse.from(memberService.signUp(signUpRequest), SIGNUP_SUCCESS.getMessage());
    }

    @GetMapping("/auth-test")
    public CommonResponse<Void> authTest() {
        return CommonResponse.from("JWT 토큰이 유효합니다.");
    }
}
