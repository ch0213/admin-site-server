package admin.adminsiteserver.member.domain;

import admin.adminsiteserver.common.domain.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String studentNumber;

    @Column(nullable = false)
    private String phoneNumber;

    private boolean deleted;

    @Embedded
    private MemberFilePath filePath;

    @Enumerated(STRING)
    private RoleType role;

    @Builder
    public Member(String email, String password, String name, String studentNumber, String phoneNumber, MemberFilePath filePath, RoleType role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.studentNumber = studentNumber;
        this.phoneNumber = phoneNumber;
        this.filePath = filePath;
        this.role = role;
        this.deleted = false;
    }

    public void update(String name, String studentNumber, String phoneNumber) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.phoneNumber = phoneNumber;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateImage(String fileName, String fileUrl) {
        this.filePath = new MemberFilePath(fileName, fileUrl);
    }

    public void updateRole(RoleType roleType) {
        this.role = roleType;
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean hasEmail() {
        return email != null && !email.isBlank();
    }

    public boolean sameStudentNumber(String studentNumber) {
        return this.studentNumber.equals(studentNumber);
    }

    public String getRoleType() {
        return role.getRole();
    }
}
