package admin.adminsiteserver.member.auth.domain;

import admin.adminsiteserver.member.member.domain.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
public class MemberAdapter extends User {

    private final Member member;

    public MemberAdapter(Member member) {
        super(String.valueOf(member.getId()), member.getPassword(), Collections.singleton(new SimpleGrantedAuthority(member.securityRoleType())));
        this.member = member;
    }
}
