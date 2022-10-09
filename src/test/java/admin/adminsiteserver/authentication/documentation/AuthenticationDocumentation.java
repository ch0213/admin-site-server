package admin.adminsiteserver.authentication.documentation;

import admin.adminsiteserver.Documentation;
import admin.adminsiteserver.authentication.application.AuthenticationService;
import admin.adminsiteserver.authentication.exception.MemberNotFoundException;
import admin.adminsiteserver.authentication.exception.WrongPasswordException;
import admin.adminsiteserver.authentication.ui.AuthenticationController;
import admin.adminsiteserver.authentication.ui.response.LoginResponse;
import admin.adminsiteserver.authentication.ui.response.TokenResponse;
import admin.adminsiteserver.member.domain.Member;
import admin.adminsiteserver.member.domain.MemberRepository;
import io.restassured.module.mockmvc.response.ValidatableMockMvcResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static admin.adminsiteserver.member.fixture.MemberFixture.회원생성;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

@DisplayName("인증 관련 기능 문서화 테스트")
@WebMvcTest(controllers = {AuthenticationController.class})
class AuthenticationDocumentation extends Documentation {
    @MockBean
    AuthenticationService authenticationService;

    private Member member;

    @BeforeEach
    void register() {
        member = 회원생성();
        setJwtTokenProvider(member, null, false);
    }

    @DisplayName("이메일과 비밀번호로 로그인을 한다.")
    @Test
    void login() {
        when(authenticationService.login(any())).thenReturn(LoginResponse.of(member, new TokenResponse("access token", "refresh token")));

        로그인_요청("/login", "login/success", member.getEmail(), member.getPassword())
                .status(HttpStatus.OK)
                .extract();
    }

    @DisplayName("이메일이 올바르지 않으면 로그인 할 수 없다.")
    @Test
    void loginWithNotFoundEmail() {
        doThrow(new MemberNotFoundException()).when(authenticationService).login(any());

        로그인_요청("/login", "login/fail-email", "wrong@email.com", member.getPassword())
                .status(HttpStatus.BAD_REQUEST)
                .extract();
    }

    @DisplayName("비밀번호가 올바르지 않으면 로그인 할 수 없다.")
    @Test
    void loginWithWrongPassword() {
        doThrow(new WrongPasswordException()).when(authenticationService).login(any());

        로그인_요청("/login", "login/fail-password", member.getEmail(), "wrongPassword")
                .status(HttpStatus.BAD_REQUEST)
                .extract();
    }

    private ValidatableMockMvcResponse 로그인_요청(String path, String identifier, String email, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post(path)
                .then().log().all()
                .apply(document(identifier,
                        getDocumentRequest(),
                        getDocumentResponse()));
    }
}
