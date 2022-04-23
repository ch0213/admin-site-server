package admin.adminsiteserver.member.member.ui;

import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.member.auth.util.LoginUser;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.member.member.application.MemberService;
import admin.adminsiteserver.member.member.application.dto.MemberDto;
import admin.adminsiteserver.member.member.ui.dto.SignUpRequest;
import admin.adminsiteserver.member.member.ui.dto.UpdateImageRequest;
import admin.adminsiteserver.member.member.ui.dto.UpdateMemberRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.util.List;

import static admin.adminsiteserver.member.member.ui.MemberResponseMessage.*;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public CommonResponse<MemberDto> signUp(SignUpRequest signUpRequest) {
        return CommonResponse.of(memberService.signUp(signUpRequest), SIGNUP_SUCCESS.getMessage());
    }

    @PutMapping
    public CommonResponse<Void> updateMember(
            @RequestBody UpdateMemberRequest updateMemberRequest,
            @LoginUser LoginUserInfo loginUserInfo
    ) {
        memberService.updateMember(updateMemberRequest, loginUserInfo.getUserId());
        return CommonResponse.from(UPDATE_SUCCESS.getMessage());
    }

    @PutMapping("/image")
    public CommonResponse<Void> updateMemberImage(
            UpdateImageRequest updateImageRequest,
            @LoginUser LoginUserInfo loginUserInfo
    ) {
        memberService.updateMemberImage(updateImageRequest.getImage(), loginUserInfo.getUserId());
        return CommonResponse.from(UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping
    public CommonResponse<Void> deleteMember(@LoginUser LoginUserInfo loginUserInfo) {
        memberService.deleteMember(loginUserInfo.getUserId());
        return CommonResponse.from(DELETE_SUCCESS.getMessage());
    }

    @GetMapping("/me")
    public CommonResponse<MemberDto> findMyself(@LoginUser LoginUserInfo loginUserInfo) {
        return CommonResponse.of(memberService.findMyself(loginUserInfo), INQUIRE_MYSELF_SUCCESS.getMessage());
    }

    @GetMapping
    public CommonResponse<List<MemberDto>> findMembers(Pageable pageable) {
        return memberService.findMembers(pageable);
    }
}
