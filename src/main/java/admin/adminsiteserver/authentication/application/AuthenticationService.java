package admin.adminsiteserver.authentication.application;

import admin.adminsiteserver.authentication.dto.response.LoginResponse;
import admin.adminsiteserver.authentication.exception.NotExistMemberException;
import admin.adminsiteserver.authentication.exception.WrongPasswordException;
import admin.adminsiteserver.authentication.dto.request.LoginRequest;
import admin.adminsiteserver.authentication.util.JwtTokenProvider;
import admin.adminsiteserver.member.domain.Member;
import admin.adminsiteserver.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(NotExistMemberException::new);
        validatePassword(loginRequest, member);
        return LoginResponse.of(member, jwtTokenProvider.createTokens(member));
    }

    private void validatePassword(LoginRequest request, Member member) {
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new WrongPasswordException();
        }
    }
}
