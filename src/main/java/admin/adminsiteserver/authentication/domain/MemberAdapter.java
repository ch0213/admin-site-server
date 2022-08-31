package admin.adminsiteserver.authentication.domain;

import admin.adminsiteserver.member.domain.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
public class MemberAdapter extends User {

    private final Member member;

    public MemberAdapter(Member member) {
        super(String.valueOf(member.getId()), member.getPassword(), Collections.singleton(new SimpleGrantedAuthority(member.getRoleType())));
        this.member = member;
    }

    public Long getId() {
        return member.getId();
    }

    public String getEmail() {
        return member.getEmail();
    }

    public String getStudentNumber() {
        return member.getStudentNumber();
    }

    public String getName() {
        return member.getName();
    }
}
