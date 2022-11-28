package admin.adminsiteserver.calendar.domain;

import admin.adminsiteserver.common.domain.RoleType;
import admin.adminsiteserver.common.exception.PermissionDeniedException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
public class Author implements Comparable<Author> {
    @Column
    private Long memberId;

    @Column
    private String name;

    @Column
    private String studentNumber;

    @Column
    private RoleType roleType;

    public Author(Long memberId, String name, String studentNumber, RoleType roleType) {
        this.memberId = memberId;
        this.name = name;
        this.studentNumber = studentNumber;
        this.roleType = roleType;
    }

    public void validate(Author author) {
        if (!hasAuthority(author)) {
            throw new PermissionDeniedException();
        }
    }

    public boolean hasAuthority(Author author) {
        return equals(author) || compareTo(author) > 0;
    }

    public boolean equalsId(Author author) {
        return this.memberId.equals(author.getMemberId());
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
        return Objects.equals(memberId, author.memberId)
                && Objects.equals(name, author.name)
                && Objects.equals(studentNumber, author.studentNumber)
                && roleType == author.roleType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, name, studentNumber, roleType);
    }

    @Override
    public int compareTo(Author o) {
        return this.roleType.compareTo(o.roleType);
    }
}
