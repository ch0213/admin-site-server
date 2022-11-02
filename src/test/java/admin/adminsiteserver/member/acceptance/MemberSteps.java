package admin.adminsiteserver.member.acceptance;

import admin.adminsiteserver.member.ui.response.MemberResponse;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static admin.adminsiteserver.AcceptanceTestSteps.given;
import static admin.adminsiteserver.member.fixture.MemberInformation.IMAGE_FILE;
import static admin.adminsiteserver.member.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class MemberSteps {

    public static ExtractableResponse<Response> 회원_등록되어_있음(String email, String password, String name,
                                                           String studentNumber, String phoneNumber, File image) {
        ExtractableResponse<Response> response = 회원_생성_요청(email, password, name, studentNumber, phoneNumber, image);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        return response;
    }

    public static ExtractableResponse<Response> 회원_생성_요청(String email, String password, String name,
                                                         String studentNumber, String phoneNumber, File image) {
        return given()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("image", image, "image/png")
                .multiPart(new MultiPartSpecBuilder(name).controlName("name").charset(StandardCharsets.UTF_8).build())
                .formParam("email", email)
                .formParam("password", password)
                .formParam("studentNumber", studentNumber)
                .formParam("phoneNumber", phoneNumber)
                .when().post("/members")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 회원_생성_요청(String email, String name, String studentNumber) {
        return 회원_생성_요청(email, 회원1.getPassword(), name, studentNumber, 회원1.getPhoneNumber(), IMAGE_FILE);
    }

    public static ExtractableResponse<Response> 회원정보_변경_요청(String token, String name, String studentNumber,
                                                           String phoneNumber) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("studentNumber", studentNumber);
        params.put("phoneNumber", phoneNumber);

        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().put("/members/me")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 회원_비밀번호_변경_요청(String token, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("password", password);

        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().put("/members/me/password")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 회원_프로필_이미지_변경_요청(String token, File image) {
        return given(token)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("image", image, "image/png")
                .when().put("/members/me/image")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 회원_탈퇴_요청(String token) {
        return given(token)
                .when().delete("/members/me")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 회원목록_조회_요청() {
        return given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 자신의_회원정보_조회_요청(String token) {
        return given(token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/me")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 아이디로_회원_정보_조회_요청(String path) {
        return given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(path)
                .then().log().all().extract();
    }

    public static void 회원정보가_올바르게_조회됨(ExtractableResponse<Response> response, String email,
                                      String name, String studentNumber, String phoneNumber) {
        MemberResponse memberResponse = response.as(MemberResponse.class);
        assertAll(
                () -> assertThat(memberResponse.getId()).isNotNull(),
                () -> assertThat(memberResponse.getEmail()).isEqualTo(email),
                () -> assertThat(memberResponse.getName()).isEqualTo(name),
                () -> assertThat(memberResponse.getStudentNumber()).isEqualTo(studentNumber),
                () -> assertThat(memberResponse.getPhoneNumber()).isEqualTo(phoneNumber)
        );
    }
}
