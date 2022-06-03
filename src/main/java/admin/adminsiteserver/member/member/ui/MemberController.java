package admin.adminsiteserver.member.member.ui;

import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.member.auth.util.LoginUser;
import admin.adminsiteserver.member.member.application.MemberQueryService;
import admin.adminsiteserver.member.member.application.MemberService;
import admin.adminsiteserver.member.member.application.dto.MemberResponse;
import admin.adminsiteserver.member.member.domain.Member;
import admin.adminsiteserver.member.member.ui.dto.SignUpRequest;
import admin.adminsiteserver.member.member.ui.dto.UpdateImageRequest;
import admin.adminsiteserver.member.member.ui.dto.UpdateMemberRequest;
import admin.adminsiteserver.member.member.ui.dto.UpdatePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static admin.adminsiteserver.member.member.ui.MemberResponseMessage.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberQueryService memberQueryService;
    private final MemberService memberService;

    @GetMapping("/members/me")
    public CommonResponse<MemberResponse> findMyself(@LoginUser Member loginMember) {
        return CommonResponse.of(MemberResponse.from(loginMember), INQUIRE_MYSELF_SUCCESS.getMessage());
    }

    @GetMapping("/members")
    public CommonResponse<List<MemberResponse>> findMembers(Pageable pageable) {
        return memberQueryService.findMembers(pageable);
    }

    @PostMapping("/signup")
    public CommonResponse<MemberResponse> signUp(@Valid SignUpRequest signUpRequest) {
        return CommonResponse.of(memberService.signUp(signUpRequest), SIGNUP_SUCCESS.getMessage());
    }

    @PutMapping("/members")
    public CommonResponse<Void> updateMember(
            @Valid @RequestBody UpdateMemberRequest updateMemberRequest,
            @LoginUser Member loginMember
    ) {
        memberService.updateInfo(updateMemberRequest, loginMember.getEmail());
        return CommonResponse.from(UPDATE_SUCCESS.getMessage());
    }

    @PutMapping("/members/password")
    public CommonResponse<Void> updatePassword(
            @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest,
            @LoginUser Member loginMember
    ) {
        memberService.updatePassword(loginMember, updatePasswordRequest);
        return CommonResponse.from(UPDATE_SUCCESS.getMessage());
    }

    @PutMapping("/members/image")
    public CommonResponse<Void> updateMemberImage(
            @LoginUser Member loginMember,
            @Valid UpdateImageRequest updateImageRequest
    ) {
        memberService.updateImage(loginMember, updateImageRequest.getImage());
        return CommonResponse.from(UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/members")
    public CommonResponse<Void> deleteMember(@LoginUser Member loginMember) {
        memberService.delete(loginMember);
        return CommonResponse.from(DELETE_SUCCESS.getMessage());
    }
}
