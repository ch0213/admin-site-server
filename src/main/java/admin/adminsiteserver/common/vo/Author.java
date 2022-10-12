package admin.adminsiteserver.common.vo;

import admin.adminsiteserver.common.domain.RoleType;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
public class Author implements Comparable<Author> {
    @Column
    private Long authorId;

    @Column
    private String authorEmail;

    @Column
    private String authorStudentNumber;

    @Column
    private String authorName;

    @Column
    private RoleType roleType;

    protected Author() {
    }

    public Author(Long authorId, String authorEmail, String authorStudentNumber, String authorName, RoleType roleType) {
        this.authorId = authorId;
        this.authorEmail = authorEmail;
        this.authorStudentNumber = authorStudentNumber;
        this.authorName = authorName;
        this.roleType = roleType;
    }

    public boolean isAdmin() {
        return roleType == RoleType.ADMIN;
    }

    public boolean isPresident() {
        return roleType == RoleType.PRESIDENT;
    }

    public boolean isOfficer() {
        return roleType == RoleType.OFFICER;
    }

    public boolean isMember() {
        return roleType == RoleType.MEMBER;
    }

    public boolean isGuest() {
        return roleType == RoleType.GUEST;
    }

    public boolean equalsId(Author author) {
        return this.authorId.equals(author.authorId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author author = (Author) o;
        return Objects.equals(getAuthorId(), author.getAuthorId())
                && Objects.equals(getAuthorEmail(), author.getAuthorEmail())
                && Objects.equals(getAuthorStudentNumber(), author.getAuthorStudentNumber())
                && Objects.equals(getAuthorName(), author.getAuthorName())
                && getRoleType() == author.getRoleType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthorId(), getAuthorEmail(), getAuthorStudentNumber(), getAuthorName(), getRoleType());
    }

    @Override
    public int compareTo(Author o) {
        return this.roleType.compareTo(o.roleType);
    }
}
