package admin.adminsiteserver.announcement.acceptance;

import admin.adminsiteserver.aws.dto.response.FilePath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static admin.adminsiteserver.AcceptanceTestSteps.given;

public class AnnouncementSteps {
    public static ExtractableResponse<Response> 공지사항_등록_요청(String token, String title, String content) {
        return AnnouncementSteps.공지사항_등록_요청(token, title, content, null);
    }

    public static ExtractableResponse<Response> 공지사항_등록_요청(String token, String title, String content, List<FilePath> files) {
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createAnnouncementParams(title, content, files))
                .when().post("/announcements")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 공지사항_수정_요청(String token, String path, String title, String content) {
        return AnnouncementSteps.공지사항_수정_요청(token, path, title, content, null);
    }

    public static ExtractableResponse<Response> 공지사항_수정_요청(String token, String path, String title, String content, List<FilePath> files) {
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createAnnouncementParams(title, content, files))
                .when().put(path)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 공지사항_삭제_요청(String token, String path) {
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(path)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 공지사항_조회_요청(String token, String path) {
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(path)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 공지사항_목록_조회_요청(String token, int page, int size) {
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("page", "" + page)
                .queryParam("size", "" + size)
                .when().get("/announcements")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 공지사항_댓글_등록_요청(String token, String path, String comment) {
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createCommentParams(comment))
                .when().post(path + "/comments")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 공지사항_댓글_수정_요청(String token, String path, String comment) {
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createCommentParams(comment))
                .when().put(path)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 공지사항_댓글_삭제_요청(String token, String path) {
        return given(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(path)
                .then().log().all().extract();
    }

    private static Map<String, Object> createAnnouncementParams(String title, String content, List<FilePath> files) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", title);
        params.put("content", content);
        params.put("files", files);
        return params;
    }

    private static Map<String, String> createCommentParams(String comment) {
        Map<String, String> params = new HashMap<>();
        params.put("comment", comment);
        return params;
    }
}
