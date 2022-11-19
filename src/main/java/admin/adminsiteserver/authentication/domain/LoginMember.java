package admin.adminsiteserver.authentication.domain;

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

    public <T> T toAuthor(AuthorConstructor<T> constructor) {
        return constructor.author(id, name, studentNumber, roleType);
    }

    public static LoginMember from(MemberAdapter memberAdapter) {
        return new LoginMember(
                memberAdapter.getId(),
                memberAdapter.getEmail(),
                memberAdapter.getStudentNumber(),
                memberAdapter.getName(),
                memberAdapter.getRoleType());
    }
}
