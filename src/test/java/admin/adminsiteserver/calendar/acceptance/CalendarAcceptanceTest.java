package admin.adminsiteserver.calendar.acceptance;

import admin.adminsiteserver.AcceptanceTest;
import admin.adminsiteserver.calendar.ui.response.CalendarResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static admin.adminsiteserver.authentication.acceptance.AuthenticationSteps.로그인되어_있음;
import static admin.adminsiteserver.calendar.acceptance.CalendarSteps.*;
import static admin.adminsiteserver.member.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("일정 인수테스트")
class CalendarAcceptanceTest extends AcceptanceTest {
    @Test
    void 일정을_생성한다() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());

        var response = 일정_등록_요청(관리자토큰, "세미나", "인프라 특강", "2022-10-30T11:00", "2022-11-11T11:11");

        CalendarResponse 일정 = 일정_조회_요청(response.header("Location")).as(CalendarResponse.class);
        assertAll(
                () -> assertThat(일정.getTitle()).isEqualTo("세미나"),
                () -> assertThat(일정.getDescription()).isEqualTo("인프라 특강")
        );
    }

    @Test
    void 회원은_일정을_생성할_수_없다() {
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());

        var response = 일정_등록_요청(회원토큰, "세미나", "인프라 특강", "2022-10-30T12:00", "2022-11-11T11:11");

        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void 비회원은_일정을_생성할_수_없다() {
        var response = 일정_등록_요청("empty token", "세미나", "인프라 특강", "2022-10-30T12:00", "2022-11-11T11:11");

        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void 일정을_수정한다() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        var response = 일정_등록_요청(관리자토큰, "세미나", "인프라 특강", "2022-10-30T11:00", "2022-11-11T11:11");

        일정_수정_요청(관리자토큰, response.header("Location"), "인프라 세미나", "특강", "2022-11-11T11:00", "2022-11-22T22:22");

        CalendarResponse 일정 = 일정_조회_요청(response.header("Location")).as(CalendarResponse.class);
        assertAll(
                () -> assertThat(일정.getTitle()).isEqualTo("인프라 세미나"),
                () -> assertThat(일정.getDescription()).isEqualTo("특강"),
                () -> assertThat(일정.getStart()).hasToString("2022-11-11T11:00"),
                () -> assertThat(일정.getEnd()).hasToString("2022-11-22T22:22")
        );
    }

    @Test
    void 다른_사람이_작성한_일정을_수정할_수_없다() {
        String 관리자1토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        String 관리자2토큰 = 로그인되어_있음(관리자2.getEmail(), 관리자2.getPassword());
        var response = 일정_등록_요청(관리자1토큰, "세미나", "인프라 특강", "2022-10-30T11:00", "2022-11-11T11:11");

        var 일정_수정_응답 = 일정_수정_요청(관리자2토큰, response.header("Location"), "인프라 세미나", "특강", "2022-11-11T11:00", "2022-11-22T22:22");

        assertThat(일정_수정_응답.statusCode())
                .isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void 작성한_사람보다_접근_권한이_높으면_일정을_수정할_수_있다() {
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        String 관리자토큰 = 로그인되어_있음(관리자2.getEmail(), 관리자2.getPassword());
        var response = 일정_등록_요청(임원토큰, "세미나", "인프라 특강", "2022-10-30T11:00", "2022-11-11T11:11");

        일정_수정_요청(관리자토큰, response.header("Location"), "인프라 세미나", "특강", "2022-11-11T11:00", "2022-11-22T22:22");

        CalendarResponse 일정 = 일정_조회_요청(response.header("Location")).as(CalendarResponse.class);
        assertAll(
                () -> assertThat(일정.getTitle()).isEqualTo("인프라 세미나"),
                () -> assertThat(일정.getDescription()).isEqualTo("특강"),
                () -> assertThat(일정.getStart()).hasToString("2022-11-11T11:00"),
                () -> assertThat(일정.getEnd()).hasToString("2022-11-22T22:22")
        );
    }

    @Test
    void 작성한_사람보다_접근_권한이_낮으면_일정을_수정할_수_없다() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        var response = 일정_등록_요청(관리자토큰, "세미나", "인프라 특강", "2022-10-30T11:00", "2022-11-11T11:11");

        var 일정_수정_응답 = 일정_수정_요청(임원토큰, response.header("Location"), "인프라 세미나", "특강", "2022-11-11T11:00", "2022-11-22T22:22");

        assertThat(일정_수정_응답.statusCode())
                .isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void 회원은_일정을_수정할_수_없다() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        var response = 일정_등록_요청(관리자토큰, "세미나", "인프라 특강", "2022-10-30T11:00", "2022-11-11T11:11");

        var 일정_수정_응답 = 일정_수정_요청(회원토큰, response.header("Location"), "인프라 세미나", "특강", "2022-11-11T11:00", "2022-11-22T22:22");

        assertThat(일정_수정_응답.statusCode())
                .isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void 비회원은_일정을_수정할_수_없다() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        var response = 일정_등록_요청(관리자토큰, "세미나", "인프라 특강", "2022-10-30T11:00", "2022-11-11T11:11");

        var 일정_수정_응답 = 일정_수정_요청("empty token", response.header("Location"), "인프라 세미나", "특강", "2022-11-11T11:00", "2022-11-22T22:22");

        assertThat(일정_수정_응답.statusCode())
                .isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void 일정을_삭제한다() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        var response = 일정_등록_요청(관리자토큰, "세미나", "인프라 특강", "2022-10-30T11:00", "2022-11-11T11:11");

        일정_삭제_요청(관리자토큰, response.header("Location"));

        assertThat(일정_목록_조회_요청(2022, 11).jsonPath().getList("."))
                .isEmpty();
    }

    @Test
    void 다른_사람이_작성한_일정을_삭제할_수_없다() {
        String 관리자1토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        String 관리자2토큰 = 로그인되어_있음(관리자2.getEmail(), 관리자2.getPassword());
        var response = 일정_등록_요청(관리자1토큰, "세미나", "인프라 특강", "2022-10-30T11:00", "2022-11-11T11:11");

        var 일정_삭제_응답 = 일정_삭제_요청(관리자2토큰, response.header("Location"));

        assertThat(일정_삭제_응답.statusCode())
                .isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void 작성한_사람보다_접근_권한이_높으면_일정을_삭제할_수_있다() {
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        String 관리자토큰 = 로그인되어_있음(관리자2.getEmail(), 관리자2.getPassword());
        var response = 일정_등록_요청(임원토큰, "세미나", "인프라 특강", "2022-10-30T11:00", "2022-11-11T11:11");

        일정_삭제_요청(관리자토큰, response.header("Location"));

        assertThat(일정_목록_조회_요청(2022, 11).jsonPath().getList("."))
                .isEmpty();
    }

    @Test
    void 작성한_사람보다_접근_권한이_낮으면_일정을_삭제할_수_없다() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        var response = 일정_등록_요청(관리자토큰, "세미나", "인프라 특강", "2022-10-30T11:00", "2022-11-11T11:11");

        var 일정_삭제_응답 = 일정_삭제_요청(임원토큰, response.header("Location"));

        assertThat(일정_삭제_응답.statusCode())
                .isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void 회원은_일정을_삭제할_수_없다() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        var response = 일정_등록_요청(관리자토큰, "세미나", "인프라 특강", "2022-10-30T11:00", "2022-11-11T11:11");

        var 일정_삭제_응답 = 일정_삭제_요청(회원토큰, response.header("Location"));

        assertThat(일정_삭제_응답.statusCode())
                .isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void 비회원은_일정을_삭제할_수_없다() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        var response = 일정_등록_요청(관리자토큰, "세미나", "인프라 특강", "2022-10-30T11:00", "2022-11-11T11:11");

        var 일정_삭제_응답 = 일정_삭제_요청("empty token", response.header("Location"));

        assertThat(일정_삭제_응답.statusCode())
                .isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void 일정_목록을_조회한다() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        일정_등록_요청(관리자토큰, "세미나1", "인프라 특강", "2022-10-30T11:00", "2022-11-11T11:11");
        일정_등록_요청(관리자토큰, "세미나2", "인프라 특강", "2022-10-30T11:00", "2022-12-01T11:11");
        일정_등록_요청(관리자토큰, "세미나3", "인프라 특강", "2022-11-05T11:00", "2022-11-10T11:11");
        일정_등록_요청(관리자토큰, "세미나4", "인프라 특강", "2022-11-25T11:00", "2022-12-01T11:11");

        일정_등록_요청(관리자토큰, "세미나5", "인프라 특강", "2022-10-25T11:00", "2022-10-31T11:11");
        일정_등록_요청(관리자토큰, "세미나6", "인프라 특강", "2022-12-01T11:00", "2022-12-02T11:11");

        var response = 일정_목록_조회_요청(2022, 11).jsonPath().getList("title", String.class);
        assertAll(
                () -> assertThat(response).hasSize(4),
                () -> assertThat(response).containsExactly("세미나1", "세미나2", "세미나3", "세미나4")
        );
    }
}
