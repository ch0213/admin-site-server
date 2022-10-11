package admin.adminsiteserver.authentication.domain;

import admin.adminsiteserver.common.vo.Author;
import admin.adminsiteserver.common.domain.RoleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginMember {
    private final Long id;

    private final String email;

    private final String studentNumber;

    private final String name;

    private final RoleType roleType;

    public static LoginMember from(MemberAdapter memberAdapter) {
        return new LoginMember(
                memberAdapter.getId(),
                memberAdapter.getEmail(),
                memberAdapter.getStudentNumber(),
                memberAdapter.getName(),
                memberAdapter.getRoleType());
    }

    public Author toAuthor() {
        return new Author(id, email, studentNumber, name, roleType);
    }
}
