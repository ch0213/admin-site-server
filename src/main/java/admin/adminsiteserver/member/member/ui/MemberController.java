package admin.adminsiteserver.member.member.ui;

import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.member.auth.util.LoginUser;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.member.member.application.MemberService;
import admin.adminsiteserver.member.member.application.dto.MemberDto;
import admin.adminsiteserver.member.member.ui.dto.SignUpRequest;
import admin.adminsiteserver.member.member.ui.dto.UpdateMemberRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static admin.adminsiteserver.member.member.ui.MemberResponseMessage.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public CommonResponse<MemberDto> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return CommonResponse.of(memberService.signUp(signUpRequest), SIGNUP_SUCCESS.getMessage());
    }

    @PutMapping("/member")
    public CommonResponse<Void> updateMember(
            @RequestBody UpdateMemberRequest updateMemberRequest,
            @LoginUser LoginUserInfo loginUserInfo
    ) {
        memberService.updateMember(updateMemberRequest, loginUserInfo.getUserId());
        return CommonResponse.from(UPDATE_SUCCESS.getMessage());
    }
}
