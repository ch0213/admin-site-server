package admin.adminsiteserver.announcement.util;

import admin.adminsiteserver.announcement.domain.Author;
import admin.adminsiteserver.member.fixture.MemberFixture;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberFixtureConverter {
    public static Author author(MemberFixture member) {
        return new Author(
                member.getId(),
                member.getName(),
                member.getStudentNumber(),
                member.getRoleType()
        );
    }
}
