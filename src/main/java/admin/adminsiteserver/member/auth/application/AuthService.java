package admin.adminsiteserver.member.auth.application;

import admin.adminsiteserver.member.auth.application.dto.LoginResponse;
import admin.adminsiteserver.member.auth.exception.NotExistMemberException;
import admin.adminsiteserver.member.auth.exception.WrongPasswordException;
import admin.adminsiteserver.member.auth.ui.dto.LoginRequest;
import admin.adminsiteserver.member.auth.util.JwtTokenProvider;
import admin.adminsiteserver.member.member.domain.Member;
import admin.adminsiteserver.member.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import java.util.Optional;

import static admin.adminsiteserver.member.member.domain.RoleType.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(NotExistMemberException::new);
        validatePassword(loginRequest, member);
        return LoginResponse.of(member, jwtTokenProvider.createTokens(member));
    }

    private void validatePassword(LoginRequest loginRequest, Member member) {
        if (!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            throw new WrongPasswordException();
        }
    }

    @PostConstruct
    public void createAdmin() {
        Member admin = Member.builder()
                .email("admin@admin.com")
                .password(passwordEncoder.encode("admin"))
                .role(ADMIN)
                .name("관리자")
                .studentNumber("201600000")
                .phoneNumber("010-0000-0000")
                .build();
        saveAdmin(admin);
    }

    @Transactional
    public void saveAdmin(Member member) {
        Optional<Member> admin = memberRepository.findByEmail("admin@admin.com");
        if (admin.isEmpty()) memberRepository.save(member);
    }
}
