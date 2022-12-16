package admin.adminsiteserver.promotion.acceptance;

import admin.adminsiteserver.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static admin.adminsiteserver.authentication.acceptance.AuthenticationSteps.로그인되어_있음;
import static admin.adminsiteserver.member.fixture.MemberFixture.*;
import static admin.adminsiteserver.promotion.acceptance.PromotionSteps.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("승진 신청 인수테스트")
class PromotionAcceptanceTest extends AcceptanceTest {
    @Test
    void 승진_신청을_생성한다() {
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());

        var response = 승진_신청(회원토큰, "임원");

        var promotions = 내_승진_신청_내역_조회(회원토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(promotions.get(0).getStatus()).isEqualTo("WAITING")
        );
    }

    @Test
    void 현재_직책보다_같거나_낮은_직책으로_신청할_수_없다() {
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());

        var response = 승진_신청(회원토큰, "회원");

        var promotions = 내_승진_신청_내역_조회(회원토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(promotions).isEmpty()
        );
    }

    @Test
    void 승진_신청을_변경한다() {
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        String path = 승진_신청(회원토큰, "임원").header("Location");

        var response = 승진_신청_변경(회원토큰, path, "회장");

        var promotions = 내_승진_신청_내역_조회(회원토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(promotions.get(0).getRegisterRoleType()).isEqualTo("회장")
        );
    }

    @ValueSource(strings = {"임원", "회원"})
    @ParameterizedTest
    void 현재_직책보다_같거나_낮은_직책으로_변경할_수_없다(String role) {
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        String path = 승진_신청(임원토큰, "회장").header("Location");

        var response = 승진_신청_변경(임원토큰, path, role);

        var promotions = 내_승진_신청_내역_조회(임원토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(promotions.get(0).getRegisterRoleType()).isEqualTo("회장")
        );
    }

    @Test
    void 다른_사람의_승진_신청을_변경할_수_없다() {
        String 회원1토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        String 회원2토큰 = 로그인되어_있음(회원2.getEmail(), 회원2.getPassword());
        String path = 승진_신청(회원1토큰, "임원").header("Location");

        var response = 승진_신청_변경(회원2토큰, path, "회장");
        var promotions = 내_승진_신청_내역_조회(회원1토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
                () -> assertThat(promotions.get(0).getRegisterRoleType()).isEqualTo("임원")
        );
    }

    @Test
    void 직책이_높더라도_다른_사람의_승진_신청을_변경할_수_없다() {
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        String path = 승진_신청(회원토큰, "임원").header("Location");

        var response = 승진_신청_변경(임원토큰, path, "회장");
        var promotion = 내_승진_신청_내역_조회(회원토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
                () -> assertThat(promotion.get(0).getRegisterRoleType()).isEqualTo("임원")
        );
    }

    @Test
    void 승진_신청을_취소한다() {
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        var path = 승진_신청(회원토큰, "임원").header("Location");

        var response = 승진_신청_취소(회원토큰, path);

        var promotions = 내_대기_중인_승진_신청_내역_조회(회원토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(promotions).isEmpty()
        );
    }

    @Test
    void 다른_사람의_승진_신청을_취소할_수_없다() {
        String 회원토큰1 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        String 회원토큰2 = 로그인되어_있음(회원2.getEmail(), 회원2.getPassword());
        var path = 승진_신청(회원토큰1, "임원").header("Location");

        var response = 승진_신청_취소(회원토큰2, path);

        var promotions = 내_대기_중인_승진_신청_내역_조회(회원토큰1);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
                () -> assertThat(promotions).isNotEmpty()
        );
    }

    @Test
    void 직책이_높더라도_다른_사람의_승진_신청을_취소할_수_없다() {
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        var path = 승진_신청(회원토큰, "임원").header("Location");

        var response = 승진_신청_취소(임원토큰, path);

        var promotions = 내_대기_중인_승진_신청_내역_조회(회원토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
                () -> assertThat(promotions).isNotEmpty()
        );
    }

    @Test
    void 관리자가_승진_신청을_수락한다() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        var path = 승진_신청(회원토큰, "임원").header("Location");

        var response = 승진_신청_수락(관리자토큰, path);

        var promotions = 승진_신청_내역_조회(관리자토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(promotions.get(0).getStatus()).isEqualTo("APPROVE")
        );
    }

    @Test
    void 회장이_승진_신청을_수락한다() {
        String 회장토큰 = 로그인되어_있음(회장1.getEmail(), 회장1.getPassword());
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        var path = 승진_신청(회원토큰, "임원").header("Location");

        var response = 승진_신청_수락(회장토큰, path);

        var promotions = 승진_신청_내역_조회(회장토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(promotions.get(0).getStatus()).isEqualTo("APPROVE")
        );
    }

    @Test
    void 임원은_승진_신청을_수락할_수_없다() {
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        var path = 승진_신청(회원토큰, "임원").header("Location");

        var response = 승진_신청_수락(임원토큰, path);

        var promotions = 내_승진_신청_내역_조회(회원토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
                () -> assertThat(promotions.get(0).getStatus()).isEqualTo("WAITING")
        );
    }

    @Test
    void 회원은_승진_신청을_수락할_수_없다() {
        String 회원1토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        String 회원2토큰 = 로그인되어_있음(회원2.getEmail(), 회원2.getPassword());
        var path = 승진_신청(회원1토큰, "임원").header("Location");

        var response = 승진_신청_수락(회원2토큰, path);

        var promotions = 내_승진_신청_내역_조회(회원1토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
                () -> assertThat(promotions.get(0).getStatus()).isEqualTo("WAITING")
        );
    }

    @Test
    void 관리자가_승진_신청_목록을_수락한다() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        String 회원토큰1 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        String 회원토큰2 = 로그인되어_있음(회원2.getEmail(), 회원2.getPassword());
        var path1 = 승진_신청(회원토큰1, "임원").header("Location");
        var path2 = 승진_신청(회원토큰2, "임원").header("Location");

        var response = 승진_신청_목록_수락(관리자토큰, ids(path1, path2));

        var promotions = 승진_신청_내역_조회(관리자토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(promotions.get(0).getStatus()).isEqualTo("APPROVE"),
                () -> assertThat(promotions.get(1).getStatus()).isEqualTo("APPROVE")
        );
    }

    @Test
    void 회장이_승진_신청_목록을_수락한다() {
        String 회장토큰 = 로그인되어_있음(회장1.getEmail(), 회장1.getPassword());
        String 회원토큰1 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        String 회원토큰2 = 로그인되어_있음(회원2.getEmail(), 회원2.getPassword());
        var path1 = 승진_신청(회원토큰1, "임원").header("Location");
        var path2 = 승진_신청(회원토큰2, "임원").header("Location");

        var response = 승진_신청_목록_수락(회장토큰, ids(path1, path2));

        var promotions = 승진_신청_내역_조회(회장토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(promotions.get(0).getStatus()).isEqualTo("APPROVE"),
                () -> assertThat(promotions.get(1).getStatus()).isEqualTo("APPROVE")
        );
    }

    @Test
    void 임원은_승진_신청_목록을_수락할_수_없다() {
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        String 회원토큰1 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        String 회원토큰2 = 로그인되어_있음(회원2.getEmail(), 회원2.getPassword());
        var path1 = 승진_신청(회원토큰1, "임원").header("Location");
        var path2 = 승진_신청(회원토큰2, "임원").header("Location");

        var response = 승진_신청_목록_수락(임원토큰, ids(path1, path2));

        var promotion1 = 승진_신청_내역_조회(회원토큰1);
        var promotion2 = 승진_신청_내역_조회(회원토큰2);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
                () -> assertThat(promotion1.get(0).getStatus()).isEqualTo("WAITING"),
                () -> assertThat(promotion2.get(1).getStatus()).isEqualTo("WAITING")
        );
    }

    @Test
    void 회원은_승진_신청_목록을_수락할_수_없다() {
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        String 회원토큰2 = 로그인되어_있음(회원2.getEmail(), 회원2.getPassword());
        var path1 = 승진_신청(회원토큰, "임원").header("Location");
        var path2 = 승진_신청(임원토큰, "회장").header("Location");

        var response = 승진_신청_목록_수락(회원토큰2, ids(path1, path2));

        var promotion1 = 승진_신청_내역_조회(회원토큰);
        var promotion2 = 승진_신청_내역_조회(임원토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
                () -> assertThat(promotion1.get(0).getStatus()).isEqualTo("WAITING"),
                () -> assertThat(promotion2.get(0).getStatus()).isEqualTo("WAITING")
        );
    }

    @Test
    void 관리자가_승진_신청을_거절한다() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        var path = 승진_신청(회원토큰, "임원").header("Location");

        var response = 승진_신청_거절(관리자토큰, path);

        var promotions = 승진_신청_내역_조회(관리자토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(promotions.get(0).getStatus()).isEqualTo("REJECT")
        );
    }

    @Test
    void 회장이_승진_신청을_거절한다() {
        String 회장토큰 = 로그인되어_있음(회장1.getEmail(), 회장1.getPassword());
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        var path = 승진_신청(회원토큰, "임원").header("Location");

        var response = 승진_신청_거절(회장토큰, path);

        var promotions = 승진_신청_내역_조회(회장토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(promotions.get(0).getStatus()).isEqualTo("REJECT")
        );
    }

    @Test
    void 임원은_승진_신청을_거절할_수_없다() {
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        var path = 승진_신청(회원토큰, "임원").header("Location");

        var response = 승진_신청_거절(임원토큰, path);

        var promotions = 내_승진_신청_내역_조회(회원토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
                () -> assertThat(promotions.get(0).getStatus()).isEqualTo("WAITING")
        );
    }

    @Test
    void 회원은_승진_신청을_거절할_수_없다() {
        String 회원1토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        String 회원2토큰 = 로그인되어_있음(회원2.getEmail(), 회원2.getPassword());
        var path = 승진_신청(회원1토큰, "임원").header("Location");

        var response = 승진_신청_거절(회원2토큰, path);

        var promotions = 내_승진_신청_내역_조회(회원1토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
                () -> assertThat(promotions.get(0).getStatus()).isEqualTo("WAITING")
        );
    }

    @Test
    void 관리자가_승진_신청_목록을_거절한다() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        String 회원토큰1 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        String 회원토큰2 = 로그인되어_있음(회원2.getEmail(), 회원2.getPassword());
        var path1 = 승진_신청(회원토큰1, "임원").header("Location");
        var path2 = 승진_신청(회원토큰2, "임원").header("Location");

        var response = 승진_신청_목록_거절(관리자토큰, ids(path1, path2));

        var promotions = 승진_신청_내역_조회(관리자토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(promotions.get(0).getStatus()).isEqualTo("REJECT"),
                () -> assertThat(promotions.get(1).getStatus()).isEqualTo("REJECT")
        );
    }

    @Test
    void 회장이_승진_신청_목록을_거절한다() {
        String 회장토큰 = 로그인되어_있음(회장1.getEmail(), 회장1.getPassword());
        String 회원토큰1 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        String 회원토큰2 = 로그인되어_있음(회원2.getEmail(), 회원2.getPassword());
        var path1 = 승진_신청(회원토큰1, "임원").header("Location");
        var path2 = 승진_신청(회원토큰2, "임원").header("Location");

        var response = 승진_신청_목록_거절(회장토큰, ids(path1, path2));

        var promotions = 승진_신청_내역_조회(회장토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(promotions.get(0).getStatus()).isEqualTo("REJECT"),
                () -> assertThat(promotions.get(1).getStatus()).isEqualTo("REJECT")
        );
    }

    @Test
    void 임원은_승진_신청_목록을_거절할_수_없다() {
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        String 회원토큰1 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        String 회원토큰2 = 로그인되어_있음(회원2.getEmail(), 회원2.getPassword());
        var path1 = 승진_신청(회원토큰1, "임원").header("Location");
        var path2 = 승진_신청(회원토큰2, "임원").header("Location");

        var response = 승진_신청_목록_거절(임원토큰, ids(path1, path2));

        var promotion1 = 승진_신청_내역_조회(회원토큰1);
        var promotion2 = 승진_신청_내역_조회(회원토큰2);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
                () -> assertThat(promotion1.get(0).getStatus()).isEqualTo("WAITING"),
                () -> assertThat(promotion2.get(1).getStatus()).isEqualTo("WAITING")
        );
    }

    @Test
    void 회원은_승진_신청_목록을_거절할_수_없다() {
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        String 회원토큰2 = 로그인되어_있음(회원2.getEmail(), 회원2.getPassword());
        var path1 = 승진_신청(회원토큰, "임원").header("Location");
        var path2 = 승진_신청(임원토큰, "회장").header("Location");

        var response = 승진_신청_목록_거절(회원토큰2, ids(path1, path2));

        var promotion1 = 승진_신청_내역_조회(회원토큰);
        var promotion2 = 승진_신청_내역_조회(임원토큰);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
                () -> assertThat(promotion1.get(0).getStatus()).isEqualTo("WAITING"),
                () -> assertThat(promotion2.get(0).getStatus()).isEqualTo("WAITING")
        );
    }

    private List<Long> ids(String... paths) {
        return Arrays.stream(paths)
                .map(path -> Long.parseLong(path.split("/")[2]))
                .collect(Collectors.toList());
    }
}
