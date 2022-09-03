package admin.adminsiteserver.member.acceptance;

import admin.adminsiteserver.AcceptanceTest;
import admin.adminsiteserver.authentication.dto.response.LoginResponse;
import admin.adminsiteserver.aws.dto.response.FilePath;
import admin.adminsiteserver.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.common.exception.ErrorResponse;
import admin.adminsiteserver.member.dto.response.MemberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import static admin.adminsiteserver.member.acceptance.MemberSteps.*;
import static admin.adminsiteserver.member.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@DisplayName("회원 관련 기능 인수테스트")
class MemberAcceptanceTest extends AcceptanceTest {
    @MockBean
    private S3Uploader s3Uploader;

    @BeforeEach
    void init() {
        when(s3Uploader.upload(any(MultipartFile.class), eq("members/")))
                .thenAnswer(i -> new FilePath(((MultipartFile) i.getArguments()[0]).getOriginalFilename(), "file-url"));
    }

    @DisplayName("회원가입을 한다.")
    @Test
    void createMember() {
        회원_생성_요청(EMAIL, PASSWORD, NAME, STUDENT_NUMBER, PHONE_NUMBER, IMAGE_FILE);

        var 토큰 = 로그인되어_있음(EMAIL, PASSWORD).as(LoginResponse.class).getTokens().getAccessToken();
        var 회원정보 = 자신의_회원정보_조회_요청(토큰);

        회원정보가_올바르게_조회됨(회원정보, EMAIL, NAME, STUDENT_NUMBER, PHONE_NUMBER);
    }

    @DisplayName("이미 가입된 학번 또는 이메일로 회원가입할 수 없다.")
    @Test
    void name() {
        회원_생성_요청(EMAIL, PASSWORD, NAME, STUDENT_NUMBER, PHONE_NUMBER, IMAGE_FILE);

        var response = 회원_생성_요청(EMAIL, PASSWORD, NAME, STUDENT_NUMBER, PHONE_NUMBER, IMAGE_FILE);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.as(ErrorResponse.class).getMessage()).isEqualTo("이메일 또는 학번이 이미 존재합니다.")
        );
    }

    @DisplayName("회원 목록을 조회한다.")
    @Test
    void findMembers() {
        회원_생성_요청("example1@email.com", "신짱구", "202000000");
        회원_생성_요청("example2@email.com", "김철수", "202100000");
        회원_생성_요청("example3@email.com", "이훈이", "202200000");

        var 회원목록 = 회원목록_조회_요청();

        assertThat(회원목록.jsonPath().getList("members.name", String.class)).containsExactly("신짱구", "김철수", "이훈이");
    }

    @DisplayName("아이디로 회원을 조회한다.")
    @Test
    void findMemberById() {
        var 회원 = 회원_등록되어_있음(EMAIL, PASSWORD, NAME, STUDENT_NUMBER, PHONE_NUMBER, IMAGE_FILE);

        var 회원정보 = 아이디로_회원_정보_조회_요청(회원.header("Location"));

        회원정보가_올바르게_조회됨(회원정보, EMAIL, NAME, STUDENT_NUMBER, PHONE_NUMBER);
    }

    @DisplayName("내 정보를 조회한다.")
    @Test
    void findMember() {
        회원_등록되어_있음(EMAIL, PASSWORD, NAME, STUDENT_NUMBER, PHONE_NUMBER, IMAGE_FILE);
        var 토큰 = 로그인되어_있음(EMAIL, PASSWORD).as(LoginResponse.class);

        var 회원정보 = 자신의_회원정보_조회_요청(토큰.getTokens().getAccessToken());

        회원정보가_올바르게_조회됨(회원정보, EMAIL, NAME, STUDENT_NUMBER, PHONE_NUMBER);
    }

    @DisplayName("내 정보를 변경한다.")
    @Test
    void updateMember() {
        회원_생성_요청(EMAIL, PASSWORD, NAME, STUDENT_NUMBER, PHONE_NUMBER, IMAGE_FILE);
        var 토큰 = 로그인되어_있음(EMAIL, PASSWORD).as(LoginResponse.class).getTokens().getAccessToken();

        회원정보_변경_요청(토큰, "신짱구", "202201010", "010-9999-9999");

        var 회원정보 = 자신의_회원정보_조회_요청(토큰);
        회원정보가_올바르게_조회됨(회원정보, EMAIL, "신짱구", "202201010", "010-9999-9999");
    }

    @DisplayName("이미 존재하는 학번으로 변경할 수 없다.")
    @Test
    void updateMemberAlreadyExistEmail() {
        회원_생성_요청(EMAIL, PASSWORD, NAME, STUDENT_NUMBER, PHONE_NUMBER, IMAGE_FILE);
        회원_생성_요청("example1@email.com", "신짱구", "202000000");
        var 토큰 = 로그인되어_있음(EMAIL, PASSWORD).as(LoginResponse.class).getTokens().getAccessToken();

        var response = 회원정보_변경_요청(토큰, NAME, "202000000", PHONE_NUMBER);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.as(ErrorResponse.class).getMessage()).isEqualTo("이미 존재하는 학번입니다.")
        );
    }

    @DisplayName("내 비밀번호를 변경한다.")
    @Test
    void updateMemberPassword() {
        회원_생성_요청(EMAIL, PASSWORD, NAME, STUDENT_NUMBER, PHONE_NUMBER, IMAGE_FILE);
        var 토큰 = 로그인되어_있음(EMAIL, PASSWORD).as(LoginResponse.class).getTokens().getAccessToken();

        회원_비밀번호_변경_요청(토큰, "q1w2e3r4");

        assertThat(로그인_요청(EMAIL, "q1w2e3r4").statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("내 프로필 이미지를 변경한다.")
    @Test
    void updateMemberProfileImage() {
        회원_생성_요청(EMAIL, PASSWORD, NAME, STUDENT_NUMBER, PHONE_NUMBER, IMAGE_FILE);
        var 토큰 = 로그인되어_있음(EMAIL, PASSWORD).as(LoginResponse.class).getTokens().getAccessToken();

        회원_프로필_이미지_변경_요청(토큰, UPDATE_IMAGE_FILE);

        var 내_정보 = 자신의_회원정보_조회_요청(토큰).as(MemberResponse.class);
        assertThat(내_정보.getImage().getFileName()).isEqualTo("pomeranian.png");
    }

    @DisplayName("회원정보를 삭제한다(회원 탈퇴한다).")
    @Test
    void deleteMember() {
        var 회원 = 회원_생성_요청(EMAIL, PASSWORD, NAME, STUDENT_NUMBER, PHONE_NUMBER, IMAGE_FILE);
        var 토큰 = 로그인되어_있음(EMAIL, PASSWORD).as(LoginResponse.class).getTokens().getAccessToken();

        회원_탈퇴_요청(토큰);

        assertThat(아이디로_회원_정보_조회_요청(회원.header("Location")).statusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
