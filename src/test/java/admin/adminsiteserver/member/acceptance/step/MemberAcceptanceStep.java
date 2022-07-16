package admin.adminsiteserver.member.acceptance.step;

import admin.adminsiteserver.auth.application.dto.LoginResponse;
import admin.adminsiteserver.member.application.dto.MemberResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberAcceptanceStep {

    public static ExtractableResponse<Response> 회원_등록되어_있음(String email, String password, String name,
                                                           String studentNumber, String phoneNumber, File image) {
        return 회원_생성을_요청(email, password, name, studentNumber, phoneNumber, image);
    }

    public static ExtractableResponse<Response> 회원_생성을_요청(String email, String password, String name,
                                                          String studentNumber, String phoneNumber, File image) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("name", name);
        params.put("studentNumber", studentNumber);
        params.put("phoneNumber", phoneNumber);

        return RestAssured.given().log().all().
                contentType(MediaType.MULTIPART_FORM_DATA_VALUE).
                multiPart("image", image, "image/png").
                formParams(params).
                when().
                post("/members").
                then().
                log().all().
                extract();
    }

    public static void 회원_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static LoginResponse 로그인_되어_있음(String email, String password) {
        ExtractableResponse<Response> response = 로그인_요청(email, password);
        return response.as(LoginResponse.class);
    }

    public static ExtractableResponse<Response> 로그인_요청(String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        return RestAssured.given().log().all().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                body(params).
                when().
                post("/login").
                then().
                log().all().
                statusCode(HttpStatus.OK.value()).
                extract();
    }

    public static ExtractableResponse<Response> 회원_정보_조회_요청(String token) {
        return RestAssured.given().log().all().
                header("Authorization", "Bearer " + token).
                accept(MediaType.APPLICATION_JSON_VALUE).
                when().
                get("/members/me").
                then().
                log().all().
                extract();
    }

    public static void 회원_정보_조회됨(ExtractableResponse<Response> response, String email) {
        MemberResponse memberResponse = response.as(MemberResponse.class);
        assertThat(memberResponse.getId()).isNotNull();
        assertThat(memberResponse.getEmail()).isEqualTo(email);
    }
}
