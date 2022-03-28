package admin.adminsiteserver.member.auth.application;

import admin.adminsiteserver.member.auth.application.dto.JwtTokenDto;
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
}
