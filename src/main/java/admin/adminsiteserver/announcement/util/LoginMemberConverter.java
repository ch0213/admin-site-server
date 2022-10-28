package admin.adminsiteserver.announcement.util;

import admin.adminsiteserver.announcement.domain.Author;
import admin.adminsiteserver.authentication.domain.LoginMember;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginMemberConverter {
    public static Author author(LoginMember loginMember) {
        return new Author(
                loginMember.getId(),
                loginMember.getName(),
                loginMember.getStudentNumber(),
                loginMember.getRoleType()
        );
    }
}
