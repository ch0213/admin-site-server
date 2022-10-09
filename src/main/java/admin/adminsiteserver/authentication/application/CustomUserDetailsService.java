package admin.adminsiteserver.authentication.application;

import admin.adminsiteserver.authentication.domain.MemberAdapter;
import admin.adminsiteserver.authentication.exception.EmptyEmailException;
import admin.adminsiteserver.member.domain.Member;
import admin.adminsiteserver.member.domain.MemberRepository;
import admin.adminsiteserver.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.valueOf(username))
                .orElseThrow(MemberNotFoundException::new);
        if (!member.hasEmail()) {
            throw new EmptyEmailException();
        }
        return new MemberAdapter(member);
    }
}
