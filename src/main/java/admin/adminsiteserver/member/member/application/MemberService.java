package admin.adminsiteserver.member.member.application;

import admin.adminsiteserver.member.member.application.dto.MemberDto;
import admin.adminsiteserver.member.member.domain.Member;
import admin.adminsiteserver.member.member.domain.MemberRepository;
import admin.adminsiteserver.member.member.exception.AlreadyExistEmailException;
import admin.adminsiteserver.member.member.exception.MemberExceptionType;
import admin.adminsiteserver.member.member.ui.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static admin.adminsiteserver.member.member.exception.MemberExceptionType.ALREADY_EXIST_EMAIL;

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
                .ifPresent(m -> {throw new AlreadyExistEmailException(ALREADY_EXIST_EMAIL);});
        return MemberDto.from(memberRepository.save(member));
    }
}
