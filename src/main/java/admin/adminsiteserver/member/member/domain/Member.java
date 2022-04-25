package admin.adminsiteserver.member.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String email;
    private String password;
    private String name;
    private String studentNumber;
    private String phoneNumber;

    @OneToOne(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private MemberFilePath filePath;

    @Enumerated(STRING)
    private RoleType role;

    @Builder
    public Member(String email, String password, String name, String studentNumber, String phoneNumber, MemberFilePath filePath, RoleType role) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.studentNumber = studentNumber;
        this.phoneNumber = phoneNumber;
        this.filePath = filePath;
        this.role = role;
    }

    public void updateMemberInfo(String name, String studentNumber, String phoneNumber) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.phoneNumber = phoneNumber;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void addProfileImage(MemberFilePath filePath) {
        this.filePath = filePath;
        filePath.includedToMember(this);
    }

    public void updateRole(RoleType roleType) {
        this.role = roleType;
    }
}
