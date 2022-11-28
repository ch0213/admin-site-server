package admin.adminsiteserver.calendar.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static admin.adminsiteserver.AcceptanceTestSteps.given;

public class CalendarSteps {
    public static ExtractableResponse<Response> 일정_등록_요청(String token, String title, String description, LocalDateTime startTime, LocalDateTime endTime) {
        return 일정_등록_요청(token, title, description, startTime.toString(), endTime.toString());
    }

    public static ExtractableResponse<Response> 일정_등록_요청(String token, String title, String description, String startTime, String endTime) {
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(calendarRequest(title, description, startTime, endTime))
                .when().post("/calendars")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 일정_수정_요청(String token, String path, String title, String description, LocalDateTime startTime, LocalDateTime endTime) {
        return 일정_수정_요청(token, path, title, description, startTime.toString(), endTime.toString());
    }

    public static ExtractableResponse<Response> 일정_수정_요청(String token, String path, String title, String description, String startTime, String endTime) {
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(calendarRequest(title, description, startTime, endTime))
                .when().put(path)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 일정_삭제_요청(String token, String path) {
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(path)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 일정_목록_조회_요청(int year, int month) {
        return given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("year", year)
                .queryParam("month", month)
                .when().get("/calendars")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 일정_조회_요청(String path) {
        return given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(path)
                .then().log().all().extract();
    }

    private static Map<String, Object> calendarRequest(String title, String description, String startTime, String endTime) {
        Map<String, Object> request = new HashMap<>();
        request.put("title", title);
        request.put("description", description);
        request.put("startTime", startTime);
        request.put("endTime", endTime);
        return request;
    }
}
