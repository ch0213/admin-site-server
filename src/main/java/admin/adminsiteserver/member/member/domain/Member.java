package admin.adminsiteserver.member.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String userId;

    private String password;

    private String email;

    private String name;

    private String studentNumber;

    private String phoneNumber;

    private RoleType role;

    @Builder
    public Member(String userId, String password, String email, String name, String studentNumber, String phoneNumber, RoleType role) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.name = name;
        this.studentNumber = studentNumber;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
