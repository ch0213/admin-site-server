package admin.adminsiteserver.member.ui;

import admin.adminsiteserver.auth.util.AuthenticationPrincipal;
import admin.adminsiteserver.common.dto.LoginMember;
import admin.adminsiteserver.member.application.MemberQueryService;
import admin.adminsiteserver.member.application.MemberService;
import admin.adminsiteserver.member.dto.response.MemberResponse;
import admin.adminsiteserver.member.dto.request.SignUpRequest;
import admin.adminsiteserver.member.dto.request.UpdateImageRequest;
import admin.adminsiteserver.member.dto.request.UpdateMemberRequest;
import admin.adminsiteserver.member.dto.request.UpdatePasswordRequest;
import admin.adminsiteserver.member.dto.response.MembersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberQueryService memberQueryService;
    private final MemberService memberService;

    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberResponse> findMembers(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberQueryService.findById(memberId));
    }

    @GetMapping("/members")
    public ResponseEntity<MembersResponse> findMembers(Pageable pageable) {
        return ResponseEntity.ok(memberQueryService.findMembers(pageable));
    }

    @PostMapping("/members")
    public ResponseEntity<Void> signUp(@Valid SignUpRequest signUpRequest) {
        Long memberId = memberService.signUp(signUpRequest);
        return ResponseEntity.created(URI.create("/members/" + memberId)).build();
    }

    @GetMapping("/members/me")
    public ResponseEntity<MemberResponse> findMyself(@AuthenticationPrincipal LoginMember member) {
        MemberResponse memberResponse = memberQueryService.findById(member.getId());
        return ResponseEntity.ok(memberResponse);
    }

    @DeleteMapping("/members/me")
    public ResponseEntity<Void> deleteMember(@AuthenticationPrincipal LoginMember member) {
        memberService.delete(member.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/members/me")
    public ResponseEntity<Void> updateMember(@Valid @RequestBody UpdateMemberRequest request, @AuthenticationPrincipal LoginMember member) {
        memberService.update(member.getId(), request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/members/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UpdatePasswordRequest request, @AuthenticationPrincipal LoginMember member) {
        memberService.updatePassword(member.getId(), request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/members/image")
    public ResponseEntity<Void> updateMemberImage(@Valid UpdateImageRequest request, @AuthenticationPrincipal LoginMember member) {
        memberService.updateImage(member.getId(), request.getImage());
        return ResponseEntity.ok().build();
    }
}
