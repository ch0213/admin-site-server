package admin.adminsiteserver.member.member.application;

import admin.adminsiteserver.member.member.application.dto.MemberDto;
import admin.adminsiteserver.member.member.domain.Member;
import admin.adminsiteserver.member.member.domain.MemberRepository;
import admin.adminsiteserver.member.member.exception.AlreadyExistUserIDException;
import admin.adminsiteserver.member.member.ui.dto.SignUpRequest;
import admin.adminsiteserver.member.member.ui.dto.UpdateMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static admin.adminsiteserver.member.member.exception.MemberExceptionType.ALREADY_EXIST_USER_ID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberDto signUp(SignUpRequest signUpRequest) {
        Member member = signUpRequest.toMember(passwordEncoder);
        memberRepository.findByUserId(member.getUserId())
                .ifPresent(m -> {
                    throw new AlreadyExistUserIDException();
                });
        return MemberDto.from(memberRepository.save(member));
    }

    @Transactional
    public void updateMember(UpdateMemberRequest updateMemberRequest, String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() ->);
        member.update(updateMemberRequest.getEmail(),
                updateMemberRequest.getName(),
                updateMemberRequest.getStudentNumber(),
                updateMemberRequest.getPhoneNumber());
    }
}
