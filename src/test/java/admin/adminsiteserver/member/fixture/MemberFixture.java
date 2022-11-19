package admin.adminsiteserver.member.fixture;

import admin.adminsiteserver.authentication.domain.AuthorConstructor;
import admin.adminsiteserver.common.domain.RoleType;
import admin.adminsiteserver.member.domain.Member;
import admin.adminsiteserver.member.domain.MemberFilePath;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static admin.adminsiteserver.common.domain.RoleType.*;

@Getter
@RequiredArgsConstructor
public enum MemberFixture {
    관리자1(1L, "admin1@admin.com", "password", "관리자1", "202200001", "01000000001", new MemberFilePath("관리자1.png", "https://cloudfront.com/members/관리자1.png"), ADMIN),
    관리자2(2L, "admin2@admin.com", "password", "관리자2", "202200002", "01000000002", new MemberFilePath("관리자2.png", "https://cloudfront.com/members/관리자2.png"), ADMIN),

    회장1(3L, "president1@admin.com", "password", "회장1", "202200003", "01000000003", new MemberFilePath("회장1.png", "https://cloudfront.com/members/회장1.png"), PRESIDENT),
    회장2(4L, "president2@admin.com", "password", "회장2", "202200004", "01000000004", new MemberFilePath("회장2.png", "https://cloudfront.com/members/회장2.png"), PRESIDENT),

    임원1(5L, "officer1@admin.com", "password", "임원1", "202200005", "01000000005", new MemberFilePath("임원1.png", "https://cloudfront.com/members/임원1.png"), OFFICER),
    임원2(6L, "officer2@admin.com", "password", "임원2", "202200006", "01000000006", new MemberFilePath("임원2.png", "https://cloudfront.com/members/임원2.png"), OFFICER),

    회원1(7L, "member1@admin.com", "password", "회원1", "202200007", "01000000007", new MemberFilePath("회원1.png", "https://cloudfront.com/members/회원1.png"), MEMBER),
    회원2(8L, "member2@admin.com", "password", "회원2", "202200008", "01000000008", new MemberFilePath("회원2.png", "https://cloudfront.com/members/회원2.png"), MEMBER),
    회원(9L, "member3@admin.com", "password", "회원3", "202200009", "01000000009", new MemberFilePath("회원3.png", "https://cloudfront.com/members/회원3.png"), MEMBER);

    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String studentNumber;
    private final String phoneNumber;
    private final MemberFilePath file;
    private final RoleType roleType;

    public Member toEntity() {
        return new Member(email, password, name, studentNumber, phoneNumber, file, roleType);
    }

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return new Member(email, passwordEncoder.encode(password), name, studentNumber, phoneNumber, file, roleType);
    }

    public Member toEntityWithId() {
        return new Member(id, email, password, name, studentNumber, phoneNumber, false, file, roleType);
    }

    public static List<Member> members() {
        return List.of(회원1.toEntityWithId(), 회원2.toEntity(), 회원.toEntity());
    }

    public <T> T author(AuthorConstructor<T> constructor) {
        return constructor.author(id, name, studentNumber, roleType);
    }
}
