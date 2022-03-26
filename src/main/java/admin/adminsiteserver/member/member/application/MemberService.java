package admin.adminsiteserver.member.member.application;

import admin.adminsiteserver.member.member.application.dto.MemberDto;
import admin.adminsiteserver.member.member.domain.Member;
import admin.adminsiteserver.member.member.domain.MemberRepository;
import admin.adminsiteserver.member.member.ui.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberDto signUp(SignUpRequest signUpRequest) {
        signUpRequest.encryptPassword(passwordEncoder);
        Member savedMember = memberRepository.save(signUpRequest.toMember());
        return MemberDto.from(savedMember);
    }
}
