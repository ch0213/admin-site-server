package admin.adminsiteserver.member.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private String studentNumber;
    private String phoneNumber;
    private boolean deleted;

    @OneToOne(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "member_file_path_id")
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

    public void updateMemberInfo(String name, String studentNumber, String phoneNumber) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.phoneNumber = phoneNumber;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void uploadProfileImage(MemberFilePath filePath) {
        this.filePath = filePath;
    }

    public void updateRole(RoleType roleType) {
        this.role = roleType;
    }

    public String securityRoleType() {
        return role.getRole();
    }

    public boolean hasEmail() {
        return email != null;
    }

    public void delete() {
        this.deleted = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return isDeleted() == member.isDeleted() && Objects.equals(getId(), member.getId()) && Objects.equals(getEmail(), member.getEmail()) && Objects.equals(getPassword(), member.getPassword()) && Objects.equals(getName(), member.getName()) && Objects.equals(getStudentNumber(), member.getStudentNumber()) && Objects.equals(getPhoneNumber(), member.getPhoneNumber()) && Objects.equals(getFilePath(), member.getFilePath()) && getRole() == member.getRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getPassword(), getName(), getStudentNumber(), getPhoneNumber(), isDeleted(), getFilePath(), getRole());
    }
}
