package admin.adminsiteserver.authentication.acceptance;

import admin.adminsiteserver.AcceptanceTest;
import admin.adminsiteserver.authentication.ui.response.LoginResponse;
import admin.adminsiteserver.aws.dto.response.FilePath;
import admin.adminsiteserver.common.exception.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import static admin.adminsiteserver.authentication.acceptance.AuthenticationSteps.로그인_요청;
import static admin.adminsiteserver.member.acceptance.MemberSteps.회원_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@DisplayName("인증 관련 기능 인수테스트")
class AuthenticationAcceptanceTest extends AcceptanceTest {
    @BeforeEach
    void init() {
        when(s3Uploader.upload(any(MultipartFile.class), eq("members/")))
                .thenAnswer(i -> new FilePath(((MultipartFile) i.getArguments()[0]).getOriginalFilename(), "file-url"));
    }

    @DisplayName("이메일과 비밀번호로 로그인을 한다.")
    @Test
    void login() {
        회원_생성_요청("email@email.com", "password", "202200000");
        var response = 로그인_요청("email@email.com", "password");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.as(LoginResponse.class).getEmail()).isEqualTo("email@email.com")
        );
    }

    @DisplayName("이메일이 올바르지 않으면 로그인 할 수 없다.")
    @Test
    void loginWithNotFoundEmail() {
        회원_생성_요청("email@email.com", "password", "202200000");
        var response = 로그인_요청("asdf@email.com", "password");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.as(ErrorResponse.class).getMessage()).isEqualTo("존재하지 않는 회원입니다.")
        );
    }

    @DisplayName("비밀번호가 올바르지 않으면 로그인 할 수 없다.")
    @Test
    void loginWithWrongPassword() {
        회원_생성_요청("email@email.com", "password", "202200000");
        var response = 로그인_요청("email@email.com", "asdf");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.as(ErrorResponse.class).getMessage()).isEqualTo("비밀번호가 잘못되었습니다.")
        );
    }
}
