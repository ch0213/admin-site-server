package admin.adminsiteserver.member.fixture;

import java.io.File;

public abstract class MemberInformation {
    public static final File IMAGE_FILE = new File(MemberInformation.class.getClassLoader().getResource("static/images/retriever.png").getPath());

    public static final File UPDATE_IMAGE_FILE = new File(MemberInformation.class.getClassLoader().getResource("static/images/pomeranian.png").getPath());

    public static final String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
}
