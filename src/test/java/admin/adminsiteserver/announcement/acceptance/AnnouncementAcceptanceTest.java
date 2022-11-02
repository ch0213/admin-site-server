package admin.adminsiteserver.announcement.acceptance;

import admin.adminsiteserver.AcceptanceTest;
import admin.adminsiteserver.aws.dto.response.FilePath;
import admin.adminsiteserver.common.exception.ErrorResponse;
import admin.adminsiteserver.member.domain.MemberEventHistory;
import admin.adminsiteserver.member.domain.MemberEventHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static admin.adminsiteserver.member.acceptance.MemberSteps.회원정보_변경_요청;
import static admin.adminsiteserver.member.fixture.MemberFixture.*;
import static admin.adminsiteserver.announcement.acceptance.AnnouncementSteps.*;
import static admin.adminsiteserver.authentication.acceptance.AuthenticationSteps.로그인되어_있음;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("공지사항 인수테스트")
class AnnouncementAcceptanceTest extends AcceptanceTest {
    @DisplayName("공지사항을 생성한다.")
    @Test
    void createAnnouncement() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        var 공지사항 = 공지사항_등록_요청(관리자토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");

        var response = 공지사항_조회_요청(관리자토큰, 공지사항.header("Location"));
        assertAll(
                () -> assertThat(공지사항.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.jsonPath().getString("title")).isEqualTo("개강총회 일정 공지"),
                () -> assertThat(response.jsonPath().getString("content")).isEqualTo("10월 22일 17시입니다.")
        );
    }

    @DisplayName("회원은 공지사항을 생성할 수 없다.")
    @Test
    void createAnnouncementWithMemberRole() {
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());

        var response = 공지사항_등록_요청(회원토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("비회원은 공지사항을 생성할 수 없다.")
    @Test
    void createAnnouncementWithGuestRole() {
        var response = 공지사항_등록_요청("empty token", "개강총회 일정 공지", "10월 22일 17시입니다.");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("공지사항을 수정한다.")
    @Test
    void updateAnnouncement() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        var 공지사항 = 공지사항_등록_요청(관리자토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");

        공지사항_수정_요청(관리자토큰, 공지사항.header("Location"), "일정 변경 공지", "금일 19시입니다.");

        var response = 공지사항_조회_요청(관리자토큰, 공지사항.header("Location"));
        assertAll(
                () -> assertThat(response.jsonPath().getString("title")).isEqualTo("일정 변경 공지"),
                () -> assertThat(response.jsonPath().getString("content")).isEqualTo("금일 19시입니다.")
        );
    }

    @DisplayName("다른 사람이 작성한 공지사항을 수정할 수 없다.")
    @Test
    void updateOtherAnnouncement() {
        String 임원1토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        String 임원2토큰 = 로그인되어_있음(임원2.getEmail(), 임원2.getPassword());
        var 공지사항 = 공지사항_등록_요청(임원1토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");

        var response = 공지사항_수정_요청(임원2토큰, 공지사항.header("Location"), "일정 변경 공지", "금일 19시입니다.");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("작성한 사람보다 접근 권한이 높으면 공지사항을 수정할 수 있다.")
    @Test
    void updateOtherAnnouncementWithHighAuthority() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        var 공지사항 = 공지사항_등록_요청(임원토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");

        공지사항_수정_요청(관리자토큰, 공지사항.header("Location"), "일정 변경 공지", "금일 19시입니다.");

        var response = 공지사항_조회_요청(관리자토큰, 공지사항.header("Location"));
        assertAll(
                () -> assertThat(response.jsonPath().getString("title")).isEqualTo("일정 변경 공지"),
                () -> assertThat(response.jsonPath().getString("content")).isEqualTo("금일 19시입니다.")
        );
    }


    @DisplayName("작성한 사람보다 접근 권한이 낮으면 공지사항을 수정할 수 없다.")
    @Test
    void updateOtherAnnouncementWithLowAuthority() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        var 공지사항 = 공지사항_등록_요청(관리자토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");

        var response = 공지사항_수정_요청(임원토큰, 공지사항.header("Location"), "일정 변경 공지", "금일 19시입니다.");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("회원은 공지사항을 수정할 수 없다.")
    @Test
    void updateAnnouncementWithMemberRole() {
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        var 공지사항 = 공지사항_등록_요청(임원토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");

        var response = 공지사항_수정_요청(회원토큰, 공지사항.header("Location"), "일정 변경 공지", "금일 19시입니다.");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("비회원은 공지사항을 수정할 수 없다.")
    @Test
    void updateAnnouncementWithGuestRole() {
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        var 공지사항 = 공지사항_등록_요청(임원토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");

        var response = 공지사항_수정_요청("empty token", 공지사항.header("Location"), "일정 변경 공지", "금일 19시입니다.");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("공지사항을 삭제한다.")
    @Test
    void deleteAnnouncement() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        var 공지사항 = 공지사항_등록_요청(관리자토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");

        var 공지사항삭제 = 공지사항_삭제_요청(관리자토큰, 공지사항.header("Location"));

        var response = 공지사항_조회_요청(관리자토큰, 공지사항.header("Location"));
        assertAll(
                () -> assertThat(공지사항삭제.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.as(ErrorResponse.class).getMessage()).isEqualTo("존재하지 않는 공지사항입니다.")
        );
    }

    @DisplayName("다른 사람이 작성한 공지사항을 삭제할 수 없다.")
    @Test
    void deleteOtherAnnouncement() {
        String 임원1토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        String 임원2토큰 = 로그인되어_있음(임원2.getEmail(), 임원2.getPassword());
        var 공지사항 = 공지사항_등록_요청(임원1토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");

        var response = 공지사항_삭제_요청(임원2토큰, 공지사항.header("Location"));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("작성한 사람보다 접근 권한이 높으면 공지사항을 삭제할 수 있다.")
    @Test
    void deleteOtherAnnouncementWithHighAuthority() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        var 공지사항 = 공지사항_등록_요청(임원토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");

        var 공지사항삭제 = 공지사항_삭제_요청(관리자토큰, 공지사항.header("Location"));

        var response = 공지사항_조회_요청(관리자토큰, 공지사항.header("Location"));
        assertAll(
                () -> assertThat(공지사항삭제.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.as(ErrorResponse.class).getMessage()).isEqualTo("존재하지 않는 공지사항입니다.")
        );
    }

    @DisplayName("작성한 사람보다 접근 권한이 낮으면 공지사항을 삭제할 수 없다.")
    @Test
    void deleteOtherAnnouncementWithLowAuthority() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        var 공지사항 = 공지사항_등록_요청(관리자토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");

        var response = 공지사항_삭제_요청(임원토큰, 공지사항.header("Location"));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("회원은 공지사항을 삭제할 수 없다.")
    @Test
    void deleteAnnouncementWithMemberRole() {
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        String 회원토큰 = 로그인되어_있음(회원1.getEmail(), 회원1.getPassword());
        var 공지사항 = 공지사항_등록_요청(임원토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");

        var response = 공지사항_삭제_요청(회원토큰, 공지사항.header("Location"));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("비회원은 공지사항을 삭제할 수 없다.")
    @Test
    void deleteAnnouncementWithGuestRole() {
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        var 공지사항 = 공지사항_등록_요청(임원토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");

        var response = 공지사항_삭제_요청("empty token", 공지사항.header("Location"));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("공지사항에 댓글을 작성한다.")
    @Test
    void createComment() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        var 공지사항 = 공지사항_등록_요청(관리자토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");

        var 댓글 = 공지사항_댓글_등록_요청(관리자토큰, 공지사항.header("Location"), "안녕하세요");

        var response = 공지사항_조회_요청(관리자토큰, 공지사항.header("Location"));
        assertAll(
                () -> assertThat(댓글.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.jsonPath().getList("comments.comment", String.class)).containsExactly("안녕하세요")
        );
    }

    @DisplayName("비회원은 공지사항에 댓글을 작성할 수 없다.")
    @Test
    void createCommentWithGuestRole() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        var 공지사항 = 공지사항_등록_요청(관리자토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");

        var response = 공지사항_댓글_등록_요청("empty token", 공지사항.header("Location"), "안녕하세요");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("댓글을 수정한다.")
    @Test
    void updateComment() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        var 공지사항 = 공지사항_등록_요청(관리자토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");
        var 댓글 = 공지사항_댓글_등록_요청(관리자토큰, 공지사항.header("Location"), "안녕하세요");

        공지사항_댓글_수정_요청(관리자토큰, 댓글.header("Location"), "반갑습니다");

        var response = 공지사항_조회_요청(관리자토큰, 공지사항.header("Location"));
        assertThat(response.jsonPath().getList("comments.comment", String.class)).containsExactly("반갑습니다");
    }

    @DisplayName("다른 사람의 댓글을 수정할 수 없다.")
    @Test
    void updateOtherComment() {
        String 임원1토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        String 임원2토큰 = 로그인되어_있음(임원2.getEmail(), 임원2.getPassword());
        var 공지사항 = 공지사항_등록_요청(임원1토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");
        var 댓글 = 공지사항_댓글_등록_요청(임원1토큰, 공지사항.header("Location"), "안녕하세요");

        var response = 공지사항_댓글_수정_요청(임원2토큰, 댓글.header("Location"), "반갑습니다");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("작성한 사람보다 접근 권한이 높으면 댓글을 수정할 수 있다.")
    @Test
    void updateOtherCommentWithHighAuthority() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        var 공지사항 = 공지사항_등록_요청(관리자토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");
        var 댓글 = 공지사항_댓글_등록_요청(임원토큰, 공지사항.header("Location"), "안녕하세요");

        공지사항_댓글_수정_요청(관리자토큰, 댓글.header("Location"), "반갑습니다");

        var response = 공지사항_조회_요청(관리자토큰, 공지사항.header("Location"));
        assertThat(response.jsonPath().getList("comments.comment", String.class)).containsExactly("반갑습니다");
    }

    @DisplayName("작성한 사람보다 접근 권한이 낮으면 댓글을 수정할 수 없다.")
    @Test
    void updateOtherCommentWithLowAuthority() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        var 공지사항 = 공지사항_등록_요청(관리자토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");
        var 댓글 = 공지사항_댓글_등록_요청(관리자토큰, 공지사항.header("Location"), "안녕하세요");

        var response = 공지사항_댓글_수정_요청(임원토큰, 댓글.header("Location"), "반갑습니다");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("비회원은 댓글을 수정할 수 없다.")
    @Test
    void updateCommentWithGuestRole() {
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        var 공지사항 = 공지사항_등록_요청(임원토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");
        var 댓글 = 공지사항_댓글_등록_요청(임원토큰, 공지사항.header("Location"), "안녕하세요");

        var response = 공지사항_댓글_수정_요청("empty token", 댓글.header("Location"), "반갑습니다");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("댓글을 삭제한다.")
    @Test
    void deleteComment() {
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        var 공지사항 = 공지사항_등록_요청(임원토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");
        var 댓글 = 공지사항_댓글_등록_요청(임원토큰, 공지사항.header("Location"), "안녕하세요");

        공지사항_댓글_삭제_요청(임원토큰, 댓글.header("Location"));

        var response = 공지사항_조회_요청(임원토큰, 공지사항.header("Location"));
        assertThat(response.jsonPath().getList("comments.comment", String.class)).isEmpty();
    }

    @DisplayName("다른 사람의 댓글을 삭제할 수 없다.")
    @Test
    void deleteOtherComment() {
        String 임원1토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        String 임원2토큰 = 로그인되어_있음(임원2.getEmail(), 임원2.getPassword());
        var 공지사항 = 공지사항_등록_요청(임원1토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");
        var 댓글 = 공지사항_댓글_등록_요청(임원1토큰, 공지사항.header("Location"), "안녕하세요");

        var response = 공지사항_댓글_삭제_요청(임원2토큰, 댓글.header("Location"));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("작성한 사람보다 접근 권한이 높으면 댓글을 삭제할 수 있다.")
    @Test
    void deleteOtherCommentWithHighAuthority() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());

        var 공지사항 = 공지사항_등록_요청(관리자토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");
        var 댓글 = 공지사항_댓글_등록_요청(임원토큰, 공지사항.header("Location"), "안녕하세요");

        공지사항_댓글_삭제_요청(관리자토큰, 댓글.header("Location"));

        var response = 공지사항_조회_요청(관리자토큰, 공지사항.header("Location"));
        assertThat(response.jsonPath().getList("comments.comment", String.class)).isEmpty();
    }

    @DisplayName("작성한 사람보다 접근 권한이 낮으면 댓글을 삭제할 수 없다.")
    @Test
    void deleteOtherCommentWithLowAuthority() {
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());

        var 공지사항 = 공지사항_등록_요청(관리자토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");
        var 댓글 = 공지사항_댓글_등록_요청(관리자토큰, 공지사항.header("Location"), "안녕하세요");

        var response = 공지사항_댓글_삭제_요청(임원토큰, 댓글.header("Location"));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("비회원은 댓글을 삭제할 수 없다.")
    @Test
    void deleteCommentWithGuestRole() {
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        var 공지사항 = 공지사항_등록_요청(임원토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");
        var 댓글 = 공지사항_댓글_등록_요청(임원토큰, 공지사항.header("Location"), "안녕하세요");

        var response = 공지사항_댓글_삭제_요청("empty token", 댓글.header("Location"));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("공지사항을 조회한다.")
    @Test
    void find() {
        List<FilePath> 첨부파일목록 = filePaths();
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        var 공지사항 = 공지사항_등록_요청(관리자토큰, "개강총회 일정 공지", "10월 22일 17시입니다.", 첨부파일목록);
        공지사항_댓글_등록_요청(관리자토큰, 공지사항.header("Location"), "안녕하세요");
        공지사항_댓글_등록_요청(관리자토큰, 공지사항.header("Location"), "반갑습니다");
        공지사항_댓글_등록_요청(관리자토큰, 공지사항.header("Location"), "Hello");

        var response = 공지사항_조회_요청(관리자토큰, 공지사항.header("Location"));

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList("comments.comment", String.class)).containsExactly("안녕하세요", "반갑습니다", "Hello"),
                () -> assertThat(response.jsonPath().getList("files.fileName", String.class)).containsExactly("파일명1", "파일명2", "파일명3"),
                () -> assertThat(response.jsonPath().getList("files.fileUrl", String.class)).containsExactly("https://file-url1.com", "https://file-url2.com", "https://file-url3.com")
        );
    }

    @DisplayName("공지사항 목록을 조회한다.")
    @Test
    void findAll() {
        List<FilePath> 첨부파일목록 = filePaths();
        String 관리자토큰 = 로그인되어_있음(관리자1.getEmail(), 관리자1.getPassword());
        공지사항_등록_요청(관리자토큰, "동아리 해커톤 기획안", "10월 22일 17시입니다.", 첨부파일목록);
        공지사항_등록_요청(관리자토큰, "동아리 해커톤 기획안_최종", "10월 22일 18시입니다.", 첨부파일목록);
        공지사항_등록_요청(관리자토큰, "동아리 해커톤 기획안_진짜_최종", "10월 22일 19시입니다.", 첨부파일목록);
        공지사항_등록_요청(관리자토큰, "동아리 해커톤 기획안_진짜_최종(진)", "10월 22일 20시입니다.", 첨부파일목록);
        공지사항_등록_요청(관리자토큰, "동아리 해커톤 기획안_진짜_최종_제출", "10월 22일 21시입니다.", 첨부파일목록);

        var response = 공지사항_목록_조회_요청(관리자토큰, Long.MAX_VALUE, 0, 3);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList("data")).hasSize(3),
                () -> assertThat(response.jsonPath().getList("data.title", String.class))
                        .containsExactly("동아리 해커톤 기획안_진짜_최종_제출", "동아리 해커톤 기획안_진짜_최종(진)", "동아리 해커톤 기획안_진짜_최종")
        );
    }

    @DisplayName("회원 정보가 변경되면 공지사항의 작성자에 변경 정보가 반영된다.")
    @Test
    void updateMember() {
        String 임원토큰 = 로그인되어_있음(임원1.getEmail(), 임원1.getPassword());
        공지사항_등록_요청(임원토큰, "개강총회 일정 공지", "10월 22일 17시입니다.");
        공지사항_등록_요청(임원토큰, "종강총회 일정 공지", "12월 15일 17시입니다.");

        회원정보_변경_요청(임원토큰, "개명했어요", "202300000", "01012344321");

        var response = 공지사항_목록_조회_요청(임원토큰, Long.MAX_VALUE, 0, 3);
        assertThat(response.jsonPath().getList("data.authorName", String.class))
                .containsExactly("개명했어요", "개명했어요");
    }

    private List<FilePath> filePaths() {
        return List.of(
                new FilePath("파일명1", "https://file-url1.com"),
                new FilePath("파일명2", "https://file-url2.com"),
                new FilePath("파일명3", "https://file-url3.com")
        );
    }
}
