package admin.adminsiteserver.authentication.acceptance;

import admin.adminsiteserver.authentication.ui.response.LoginResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static admin.adminsiteserver.AcceptanceTestSteps.given;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticationSteps {
    public static String 로그인되어_있음(String email, String password) {
        var response = 로그인_요청(email, password);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        return response.as(LoginResponse.class).getTokens().getAccessToken();
    }

    public static ExtractableResponse<Response> 로그인_요청(String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        return given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/login")
                .then().log().all().extract();
    }
}
