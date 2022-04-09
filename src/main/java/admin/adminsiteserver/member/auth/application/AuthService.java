package admin.adminsiteserver.member.auth.application;

import admin.adminsiteserver.member.auth.application.dto.JwtTokenDto;
import admin.adminsiteserver.member.auth.application.dto.LoginResponse;
import admin.adminsiteserver.member.auth.exception.NotExistMemberException;
import admin.adminsiteserver.member.auth.exception.WrongPasswordException;
import admin.adminsiteserver.member.auth.ui.dto.LoginRequest;
import admin.adminsiteserver.member.auth.util.JwtTokenProvider;
import admin.adminsiteserver.member.member.domain.Member;
import admin.adminsiteserver.member.member.domain.MemberRepository;
import admin.adminsiteserver.member.member.domain.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import static admin.adminsiteserver.member.member.domain.RoleType.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByUserId(loginRequest.getUserId())
                .orElseThrow(NotExistMemberException::new);

        if (isWrongPassword(loginRequest, member)) {
            throw new WrongPasswordException();
        }

        JwtTokenDto tokens = jwtTokenProvider.createTokens(member);
        return LoginResponse.of(member, tokens);
    }

    private boolean isWrongPassword(LoginRequest loginRequest, Member member) {
        return !passwordEncoder.matches(loginRequest.getPassword(), member.getPassword());
    }

    @PostConstruct
    public void createAdmin() {
        Member admin = Member.builder()
                .userId("admin")
                .password(passwordEncoder.encode("admin"))
                .role(ADMIN)
                .name("관리자")
                .studentNumber("000000000")
                .phoneNumber("010-0000-0000")
                .build();
        saveAdmin(admin);
    }

    @Transactional
    public void saveAdmin(Member admin) {
        memberRepository.save(admin);
    }
}
