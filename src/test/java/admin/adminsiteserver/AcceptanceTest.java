package admin.adminsiteserver;

import admin.adminsiteserver.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.member.domain.Member;
import admin.adminsiteserver.member.domain.MemberRepository;
import admin.adminsiteserver.utils.CacheCleanup;
import admin.adminsiteserver.utils.DatabaseCleanup;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static admin.adminsiteserver.member.fixture.MemberFixture.*;
import static admin.adminsiteserver.member.fixture.MemberFixture.회원2;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {
    @Value("${local.server.port}")
    int port;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    protected CacheCleanup cacheCleanup;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @MockBean
    protected S3Uploader s3Uploader;

    @BeforeEach
    public void setUp() {
        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port;
            databaseCleanup.afterPropertiesSet();
        }
        databaseCleanup.execute();
        cacheCleanup.execute();
        saveMembers();
    }

    private void saveMembers() {
        List<Member> members = List.of(
                관리자1.toEntity(passwordEncoder), 관리자2.toEntity(passwordEncoder),
                회장1.toEntity(passwordEncoder), 회장2.toEntity(passwordEncoder),
                임원1.toEntity(passwordEncoder), 임원2.toEntity(passwordEncoder),
                회원1.toEntity(passwordEncoder), 회원2.toEntity(passwordEncoder)
        );
        memberRepository.saveAll(members);
    }
}
