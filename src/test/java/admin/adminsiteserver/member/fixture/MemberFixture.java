package admin.adminsiteserver.member.fixture;

import admin.adminsiteserver.member.domain.Member;
import admin.adminsiteserver.member.domain.MemberFilePath;

import java.io.File;

import static admin.adminsiteserver.member.domain.RoleType.MEMBER;

public abstract class MemberFixture {

    public static Long id = 1L;
    public static String email = "email@email.com";
    public static String password = "password";
    public static String name = "홍길동";
    public static String studentNumber = "201600000";
    public static String phoneNumber = "010-0000-0000";
    public static MemberFilePath image = new MemberFilePath("치킨.png", "https://cloudfront.com/admin");
    public static File imageFile = new File(MemberFixture .class.getClassLoader().getResource("static/images/retriever.png").getPath());
    public static String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    public static Member 홍길동 = new Member(id, email, password, name, studentNumber, phoneNumber, false, image, MEMBER);
    public static Member 신짱구 = new Member(email, password, "신짱구", studentNumber, phoneNumber, image, MEMBER);
}
