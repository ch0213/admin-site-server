package admin.adminsiteserver.member.member.domain;

import admin.adminsiteserver.announcement.domain.AnnouncementFilePath;
import admin.adminsiteserver.levelup.exception.NotExistRoleException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Arrays;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.*;
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

    @OneToOne(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private MemberFilePath filePath;

    @Enumerated(STRING)
    private RoleType role;

    @Builder
    public Member(String userId, String password, String email, String name, String studentNumber, String phoneNumber, MemberFilePath filePath, RoleType role) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.name = name;
        this.studentNumber = studentNumber;
        this.phoneNumber = phoneNumber;
        this.filePath = filePath;
        this.role = role;
    }

    public void update(String email, String name, String studentNumber, String phoneNumber) {
        this.email = email;
        this.name = name;
        this.studentNumber = studentNumber;
        this.phoneNumber = phoneNumber;
    }

    public void addProfileImage(MemberFilePath filePath) {
        this.filePath = filePath;
        filePath.includedToMember(this);
    }

    public void updateRole(String newRole) {
        this.role = Arrays.stream(RoleType.values()).sequential()
                .filter(roleType -> roleType.getDescription().equals(newRole))
                .findAny()
                .orElseThrow(NotExistRoleException::new);
    }
}
