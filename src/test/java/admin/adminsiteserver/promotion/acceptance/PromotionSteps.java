package admin.adminsiteserver.promotion.acceptance;

import admin.adminsiteserver.promotion.ui.request.PromotionRequest;
import admin.adminsiteserver.promotion.ui.response.PromotionResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static admin.adminsiteserver.AcceptanceTestSteps.given;

public class PromotionSteps {
    public static ExtractableResponse<Response> 승진_신청(String token, String role) {
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new PromotionRequest(role))
                .when().post("/promotions")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 승진_신청_변경(String token, String path, String role) {
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new PromotionRequest(role))
                .when().put(path)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 승진_신청_취소(String token, String path) {
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(path)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 승진_신청_수락(String token, String path) {
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post(path + "/approve")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 승진_신청_목록_수락(String token, List<Long> promotionIds) {
        Map<String, Object> params = new HashMap<>();
        params.put("promotionIds", promotionIds);
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/promotions/approve")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 승진_신청_거절(String token, String path) {
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post(path + "/reject")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 승진_신청_목록_거절(String token, List<Long> promotionIds) {
        Map<String, Object> params = new HashMap<>();
        params.put("promotionIds", promotionIds);
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/promotions/reject")
                .then().log().all().extract();
    }

    public static List<PromotionResponse> 승진_신청_내역_조회(String token) {
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/promotions")
                .then().log().all().extract()
                .jsonPath().getList(".", PromotionResponse.class);
    }

    public static List<PromotionResponse> 내_승진_신청_내역_조회(String token) {
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/promotions/me")
                .then().log().all().extract()
                .jsonPath().getList(".", PromotionResponse.class);
    }

    public static List<PromotionResponse> 내_대기_중인_승진_신청_내역_조회(String token) {
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("type", "WAITING")
                .when().get("/promotions/me")
                .then().log().all().extract()
                .jsonPath().getList(".", PromotionResponse.class);
    }
}
