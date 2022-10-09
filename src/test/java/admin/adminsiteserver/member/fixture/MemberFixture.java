package admin.adminsiteserver.member.fixture;

import admin.adminsiteserver.member.domain.Member;
import admin.adminsiteserver.member.domain.MemberFilePath;
import admin.adminsiteserver.member.ui.response.MemberResponse;
import admin.adminsiteserver.member.ui.response.MembersResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;

import java.io.File;
import java.util.List;

import static admin.adminsiteserver.member.domain.RoleType.ADMIN;
import static admin.adminsiteserver.member.domain.RoleType.MEMBER;

public class MemberFixture {
    public static final Long ID = 1L;

    public static final String EMAIL = "email@gmail.com";

    public static final String PASSWORD = "password";

    public static final String NAME = "홍길동";

    public static final String STUDENT_NUMBER = "201600000";

    public static final String PHONE_NUMBER = "010-0000-0000";

    public static final MemberFilePath IMAGE_MULTIPART_FILE = new MemberFilePath("retriever.png", "https://cloudfront.com/members/retriever.png");

    public static final File IMAGE_FILE = new File(MemberFixture.class.getClassLoader().getResource("static/images/retriever.png").getPath());

    public static final File UPDATE_IMAGE_FILE = new File(MemberFixture.class.getClassLoader().getResource("static/images/pomeranian.png").getPath());

    public static final String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    public static Member 회원생성() {
        return new Member(ID, EMAIL, PASSWORD, NAME, STUDENT_NUMBER, PHONE_NUMBER, false, IMAGE_MULTIPART_FILE, MEMBER);
    }

    public static Member 관리자생성() {
        return new Member(ID, EMAIL, PASSWORD, NAME, STUDENT_NUMBER, PHONE_NUMBER, false, IMAGE_MULTIPART_FILE, ADMIN);
    }

    public static Member 회장생성() {
        return new Member(ID, EMAIL, PASSWORD, NAME, STUDENT_NUMBER, PHONE_NUMBER, false, IMAGE_MULTIPART_FILE, ADMIN);
    }

    public static Member 임원생성() {
        return new Member(ID, EMAIL, PASSWORD, NAME, STUDENT_NUMBER, PHONE_NUMBER, false, IMAGE_MULTIPART_FILE, ADMIN);
    }

    public static MembersResponse 회원목록_생성() {
        MemberResponse 신짱구 = MemberResponse.from(new Member(1L, "example0@email.com", "password",
                "신짱구", "202000000", "010-0000-0000",
                false, new MemberFilePath("file-name-0", "file-url-0"), MEMBER));

        MemberResponse 김철수 = MemberResponse.from(new Member(2L, "example1@email.com", "password",
                "김철수", "202000001", "010-0000-0001",
                false, new MemberFilePath("file-name-1", "file-url-1"), MEMBER));

        MemberResponse 이훈이 = MemberResponse.from(new Member(3L, "example2@email.com", "password",
                "이훈이", "202000002", "010-0000-0002",
                false, new MemberFilePath("file-name-2", "file-url-2"), MEMBER));

        MemberResponse 신형만 = MemberResponse.from(new Member(4L, "example3@email.com", "password",
                "신형만", "202000003", "010-0000-0003",
                false, new MemberFilePath("file-name-3", "file-url-3"), MEMBER));

        MemberResponse 흰둥이 = MemberResponse.from(new Member(5L, "example4@email.com", "password",
                "흰둥이", "202000004", "010-0000-0004",
                false, new MemberFilePath("file-name-4", "file-url-4"), MEMBER));

        List<MemberResponse> members = List.of(신짱구, 김철수, 이훈이, 신형만, 흰둥이);
        return MembersResponse.from(PageableExecutionUtils.getPage(members, PageRequest.of(1, 5), members::size));
    }
}
