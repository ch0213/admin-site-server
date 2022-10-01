package admin.adminsiteserver.member.documentation;

import admin.adminsiteserver.Documentation;
import admin.adminsiteserver.authentication.domain.MemberAdapter;
import admin.adminsiteserver.authentication.util.JwtTokenProvider;
import admin.adminsiteserver.member.application.MemberQueryService;
import admin.adminsiteserver.member.application.MemberService;
import admin.adminsiteserver.member.ui.response.MemberResponse;
import admin.adminsiteserver.member.exception.MemberAlreadyExistException;
import admin.adminsiteserver.member.exception.StudentNumberAlreadyExistException;
import admin.adminsiteserver.member.ui.MemberController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static admin.adminsiteserver.member.fixture.MemberFixture.*;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

@DisplayName("회원 관련 기능 문서화 테스트")
@WebMvcTest(controllers = {MemberController.class})
class MemberDocumentation extends Documentation {
    @MockBean
    MemberQueryService memberQueryService;

    @MockBean
    MemberService memberService;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @MockBean
    AuthenticationEntryPoint authenticationEntryPoint;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        super.setUp(context, restDocumentation);

        MemberAdapter memberAdapter = new MemberAdapter(회원_생성());
        when(jwtTokenProvider.getAuthentication(any()))
                .thenReturn(new UsernamePasswordAuthenticationToken(memberAdapter, "", memberAdapter.getAuthorities()));
        when(jwtTokenProvider.extractToken(any())).thenReturn("");
        when(jwtTokenProvider.validateToken(any())).thenReturn(true);
    }

    @DisplayName("회원 가입을 한다.")
    @Test
    void signup() {
        when(memberService.signUp(any())).thenReturn(회원_생성().getId());

        Map<String, Object> params = new HashMap<>();
        params.put("email", EMAIL);
        params.put("password", PASSWORD);
        params.put("name", NAME);
        params.put("studentNumber", STUDENT_NUMBER);
        params.put("phoneNumber", PHONE_NUMBER);

        given().log().all()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .formParams(params)
                .multiPart("image", IMAGE_FILE, "image/png")
                .when().post("/members")
                .then().log().all()
                .status(HttpStatus.CREATED)
                .apply(document("members/post",
                        getDocumentRequest(),
                        getDocumentResponse()))
                .extract();
    }

    @DisplayName("이미 가입된 이메일이나 학번으로 가입할 수 없다.")
    @Test
    void signupExistEmailOrStudentNumber() {
        when(memberService.signUp(any())).thenThrow(new MemberAlreadyExistException());

        Map<String, Object> params = new HashMap<>();
        params.put("email", EMAIL);
        params.put("password", PASSWORD);
        params.put("name", NAME);
        params.put("studentNumber", STUDENT_NUMBER);
        params.put("phoneNumber", PHONE_NUMBER);

        given().log().all()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .formParams(params)
                .multiPart("image", IMAGE_FILE, "image/png")
                .when().post("/members")
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .apply(document("members/post/fail",
                        getDocumentRequest(),
                        getDocumentResponse()))
                .extract();
    }

    @DisplayName("내 정보를 조회한다.")
    @Test
    void findMe() {
        when(memberQueryService.findById(any())).thenReturn(MemberResponse.from(회원_생성()));

        given().log().all()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .when().get("/members/me")
                .then().log().all()
                .status(HttpStatus.OK)
                .apply(document("members/me/get",
                        getDocumentRequest(),
                        getDocumentResponse()))
                .extract();
    }

    @DisplayName("아이디로 회원을 조회한다.")
    @Test
    void findMemberById() {
        when(memberQueryService.findById(any())).thenReturn(MemberResponse.from(회원_생성()));

        given().log().all()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .when().get("/members/{memberId}", 1L)
                .then().log().all()
                .status(HttpStatus.OK)
                .apply(document("members/id/get",
                        getDocumentRequest(),
                        getDocumentResponse()))
                .extract();
    }

    @DisplayName("회원 목록을 조회한다.")
    @Test
    void findMembers() {
        when(memberQueryService.findMembers(any())).thenReturn(회원목록_생성());

        given().log().all()
                .when().get("/members")
                .then().log().all()
                .status(HttpStatus.OK)
                .apply(document("members/get",
                        getDocumentRequest(),
                        getDocumentResponse()))
                .extract();
    }

    @DisplayName("회원 정보를 변경한다.")
    @Test
    void updateMember() {
        doNothing().when(memberService).update(any(), any());

        Map<String, Object> params = new HashMap<>();
        params.put("name", "김짱구");
        params.put("studentNumber", "202100000");
        params.put("phoneNumber", "010-1111-1111");

        given().log().all()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/members/me")
                .then().log().all()
                .status(HttpStatus.OK)
                .apply(document("members/me/put",
                        getDocumentRequest(),
                        getDocumentResponse()))
                .extract();
    }

    @DisplayName("이미 존재하는 학번으로 변경할 수 없다.")
    @Test
    void updateMemberExistEmailOrStudentNumber() {
        doThrow(new StudentNumberAlreadyExistException()).when(memberService).update(any(), any());

        Map<String, Object> params = new HashMap<>();
        params.put("name", "김짱구");
        params.put("studentNumber", "202100000");
        params.put("phoneNumber", "010-1111-1111");

        given().log().all()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/members/me")
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .apply(document("members/me/put/fail",
                        getDocumentRequest(),
                        getDocumentResponse()))
                .extract();
    }

    @DisplayName("비밀번호를 변경한다.")
    @Test
    void updateMemberPassword() {
        Map<String, Object> params = new HashMap<>();
        params.put("password", "asdf1234");

        given().log().all()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/members/me/password")
                .then().log().all()
                .status(HttpStatus.OK)
                .apply(document("members/me/password/put",
                        getDocumentRequest(),
                        getDocumentResponse()))
                .extract();
    }

    @DisplayName("프로필 이미지를 변경한다.")
    @Test
    void updateMemberProfileImage() {
        given().log().all()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("image", UPDATE_IMAGE_FILE, "image/png")
                .when().put("/members/me/image")
                .then().log().all()
                .status(HttpStatus.OK)
                .apply(document("members/me/image/put",
                        getDocumentRequest(),
                        getDocumentResponse()))
                .extract();
    }

    @DisplayName("회원 탈퇴를 한다.")
    @Test
    void delete() {
        given().log().all()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .when().delete("/members/me")
                .then().log().all()
                .status(HttpStatus.NO_CONTENT)
                .apply(document("members/me/delete",
                        getDocumentRequest(),
                        getDocumentResponse()))
                .extract();
    }
}
