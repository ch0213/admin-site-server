package admin.adminsiteserver.member.member.ui;

import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.member.auth.util.LoginUser;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.member.member.application.MemberService;
import admin.adminsiteserver.member.member.application.dto.MemberResponse;
import admin.adminsiteserver.member.member.ui.dto.SignUpRequest;
import admin.adminsiteserver.member.member.ui.dto.UpdateImageRequest;
import admin.adminsiteserver.member.member.ui.dto.UpdateMemberRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static admin.adminsiteserver.member.member.ui.MemberResponseMessage.*;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public CommonResponse<MemberResponse> signUp(@Valid SignUpRequest signUpRequest) {
        return CommonResponse.of(memberService.signUp(signUpRequest), SIGNUP_SUCCESS.getMessage());
    }

    @PutMapping("/members")
    public CommonResponse<Void> updateMember(
            @Valid @RequestBody UpdateMemberRequest updateMemberRequest,
            @LoginUser LoginUserInfo loginUserInfo
    ) {
        memberService.updateMember(updateMemberRequest, loginUserInfo.getEmail());
        return CommonResponse.from(UPDATE_SUCCESS.getMessage());
    }

    @PutMapping("/members/image")
    public CommonResponse<Void> updateMemberImage(
            @Valid UpdateImageRequest updateImageRequest,
            @LoginUser LoginUserInfo loginUserInfo
    ) {
        memberService.updateMemberImage(updateImageRequest.getImage(), loginUserInfo.getEmail());
        return CommonResponse.from(UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/members")
    public CommonResponse<Void> deleteMember(@LoginUser LoginUserInfo loginUserInfo) {
        memberService.deleteMember(loginUserInfo.getEmail());
        return CommonResponse.from(DELETE_SUCCESS.getMessage());
    }

    @GetMapping("/members/me")
    public CommonResponse<MemberResponse> findMyself(@LoginUser LoginUserInfo loginUserInfo) {
        return CommonResponse.of(memberService.findMyself(loginUserInfo), INQUIRE_MYSELF_SUCCESS.getMessage());
    }

    @GetMapping("/members")
    public CommonResponse<List<MemberResponse>> findMembers(Pageable pageable) {
        return memberService.findMembers(pageable);
    }
}
