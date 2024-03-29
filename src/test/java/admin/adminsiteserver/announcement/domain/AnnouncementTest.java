package admin.adminsiteserver.announcement.domain;

import admin.adminsiteserver.common.domain.RoleType;
import admin.adminsiteserver.common.exception.PermissionDeniedException;
import admin.adminsiteserver.member.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static admin.adminsiteserver.announcement.fixture.AnnouncementCommentFixture.*;
import static admin.adminsiteserver.announcement.fixture.AnnouncementFilePathFixture.*;
import static admin.adminsiteserver.announcement.fixture.AnnouncementFixture.*;
import static admin.adminsiteserver.member.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("공지사항 단위테스트")
class AnnouncementTest {
    @DisplayName("공지사항을 생성한다.")
    @Test
    void create() {
        Author author = new Author(1L, "202200000", "홍길동", RoleType.MEMBER);
        var filePaths = List.of(공지사항_첨부파일1.toEntityWithId(), 공지사항_첨부파일2.toEntityWithId());

        assertAll(
                () -> assertDoesNotThrow(() -> new Announcement(author, "공지사항 제목", "공지사항 내용", Collections.emptyList())),
                () -> assertDoesNotThrow(() -> new Announcement(author, "공지사항 제목", "공지사항 내용", filePaths))
        );
    }

    @DisplayName("공지사항을 변경한다.")
    @Test
    void update() {
        Announcement announcement = 회장1_공지사항.toEntityWithId();
        var filePaths = List.of(공지사항_첨부파일1.toEntityWithId(), 공지사항_첨부파일2.toEntityWithId());

        announcement.update("변경 후 제목", "변경 후 내용", filePaths, author(회장1));

        assertAll(
                () -> assertThat(announcement.getTitle()).isEqualTo("변경 후 제목"),
                () -> assertThat(announcement.getContent()).isEqualTo("변경 후 내용"),
                () -> assertThat(announcement.getFiles().getNotDeletedFiles()).isEqualTo(filePaths)
        );
    }

    @DisplayName("다른 사람이 작성한 공지사항을 변경할 수 없다.")
    @Test
    void updateOther() {
        Announcement announcement = 임원1_공지사항.toEntityWithId();
        var filePaths = List.of(공지사항_첨부파일1.toEntityWithId(), 공지사항_첨부파일2.toEntityWithId());

        assertThatThrownBy(() -> announcement.update("변경 후 제목", "변경 후 내용", filePaths, author(임원2)))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @DisplayName("작성한 사람보다 접근 권한이 높으면 공지사항을 변경할 수 있다.")
    @Test
    void updateOtherWithHighAuthority() {
        Announcement announcement = 임원1_공지사항.toEntityWithId();

        var filePaths = List.of(공지사항_첨부파일1.toEntityWithId(), 공지사항_첨부파일2.toEntityWithId());
        announcement.update("변경 후 제목", "변경 후 내용", filePaths, author(회장1));

        assertAll(
                () -> assertThat(announcement.getTitle()).isEqualTo("변경 후 제목"),
                () -> assertThat(announcement.getContent()).isEqualTo("변경 후 내용"),
                () -> assertThat(announcement.getFiles().getNotDeletedFiles()).isEqualTo(filePaths)
        );
    }

    @DisplayName("작성한 사람보다 접근 권한이 낮으면 공지사항을 변경할 수 없다.")
    @Test
    void updateOtherWithLowAuthority() {
        Announcement announcement = 회장1_공지사항.toEntityWithId();

        var filePaths = List.of(공지사항_첨부파일1.toEntityWithId(), 공지사항_첨부파일2.toEntityWithId());
        assertThatThrownBy(() -> announcement.update("변경 후 제목", "변경 후 내용", filePaths, author(임원1)))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @DisplayName("공지사항을 삭제한다.")
    @Test
    void delete() {
        Announcement announcement = 임원1_공지사항.toEntityWithId();

        announcement.delete(author(임원1));

        assertThat(announcement.isDeleted()).isTrue();
    }

    @DisplayName("다른 사람이 작성한 공지사항을 삭제할 수 없다.")
    @Test
    void deleteOther() {
        Announcement announcement = 임원1_공지사항.toEntityWithId();

        assertThatThrownBy(() -> announcement.delete(author(임원2)))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @DisplayName("작성한 사람보다 접근 권한이 높으면 공지사항을 삭제할 수 있다.")
    @Test
    void deleteOtherWithHighAuthority() {
        Announcement announcement = 임원1_공지사항.toEntityWithId();

        announcement.delete(author(회장1));

        assertThat(announcement.isDeleted()).isTrue();
    }

    @DisplayName("작성한 사람보다 접근 권한이 낮으면 공지사항을 삭제할 수 있다.")
    @Test
    void deleteOtherWithLowAuthority() {
        Announcement announcement = 회장1_공지사항.toEntityWithId();

        assertThatThrownBy(() -> announcement.delete(author(임원1)))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @DisplayName("공지사항에 댓글을 작성한다.")
    @Test
    void addComment() {
        Announcement announcement = 임원1_공지사항.toEntityWithId();

        announcement.addComment("안녕하세요", author(회장1));
        announcement.addComment("반갑습니다", author(회원1));

        assertThat(extractComments(announcement)).containsExactly("안녕하세요", "반갑습니다");
    }

    @DisplayName("공지사항의 댓글을 변경한다.")
    @Test
    void updateComment() {
        AnnouncementComment comment1 = 회원1_공지사항댓글.toEntityWithId();
        AnnouncementComment comment2 = 회원2_공지사항댓글.toEntityWithId();
        AnnouncementComment comment3 = 회원_공지사항댓글.toEntityWithId();
        Announcement announcement = 임원1_공지사항.toEntity(announcementComments(comment1, comment2, comment3));

        announcement.updateComment(comment2.getId(), "변경했어요", author(회원2));

        assertThat(extractComments(announcement)).containsExactly(comment1.getComment(), "변경했어요", comment3.getComment());
    }

    @DisplayName("다른 사람이 작성한 댓글을 변경할 수 없다.")
    @Test
    void updateOtherComment() {
        AnnouncementComment comment1 = 회원1_공지사항댓글.toEntityWithId();
        AnnouncementComment comment2 = 회원2_공지사항댓글.toEntityWithId();
        AnnouncementComment comment3 = 회원_공지사항댓글.toEntityWithId();
        Announcement announcement = 임원1_공지사항.toEntity(announcementComments(comment1, comment2, comment3));

        assertThatThrownBy(() -> announcement.updateComment(comment2.getId(), "변경했어요", author(회원1)))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @DisplayName("작성한 사람보다 접근 권한이 높으면 댓글을 변경할 수 있다.")
    @Test
    void updateCommentWithHighAuthority() {
        AnnouncementComment comment1 = 회원1_공지사항댓글.toEntityWithId();
        AnnouncementComment comment2 = 회원2_공지사항댓글.toEntityWithId();
        AnnouncementComment comment3 = 회원_공지사항댓글.toEntityWithId();
        Announcement announcement = 임원1_공지사항.toEntity(announcementComments(comment1, comment2, comment3));

        announcement.updateComment(comment2.getId(), "변경했어요", author(임원1));

        assertThat(extractComments(announcement)).containsExactly(comment1.getComment(), "변경했어요", comment3.getComment());
    }

    @DisplayName("작성한 사람보다 접근 권한이 낮으면 댓글을 변경할 수 없다.")
    @Test
    void updateCommentWithLowAuthority() {
        AnnouncementComment comment1 = 회장1_공지사항댓글.toEntityWithId();
        AnnouncementComment comment2 = 회장2_공지사항댓글.toEntityWithId();
        Announcement announcement = 임원1_공지사항.toEntity(announcementComments(comment1, comment2));

        assertThatThrownBy(() -> announcement.updateComment(comment2.getId(), "변경했어요", author(임원1)))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @DisplayName("공지사항의 삭제을 변경한다.")
    @Test
    void deleteComment() {
        AnnouncementComment comment1 = 회원1_공지사항댓글.toEntityWithId();
        AnnouncementComment comment2 = 회원2_공지사항댓글.toEntityWithId();
        AnnouncementComment comment3 = 회원_공지사항댓글.toEntityWithId();
        Announcement announcement = 임원1_공지사항.toEntity(announcementComments(comment1, comment2, comment3));

        announcement.deleteComment(comment2.getId(), author(회원2));

        assertThat(extractComments(announcement)).containsExactly(comment1.getComment(), comment3.getComment());
    }

    @DisplayName("다른 사람이 작성한 댓글을 삭제할 수 없다.")
    @Test
    void deleteOtherComment() {
        AnnouncementComment comment1 = 회원1_공지사항댓글.toEntityWithId();
        AnnouncementComment comment2 = 회원2_공지사항댓글.toEntityWithId();
        AnnouncementComment comment3 = 회원_공지사항댓글.toEntityWithId();
        Announcement announcement = 임원1_공지사항.toEntity(announcementComments(comment1, comment2, comment3));

        assertThatThrownBy(() -> announcement.deleteComment(comment2.getId(), author(회원1)))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @DisplayName("작성한 사람보다 접근 권한이 높으면 댓글을 삭제할 수 있다.")
    @Test
    void deleteCommentWithHighAuthority() {
        AnnouncementComment comment1 = 회원1_공지사항댓글.toEntityWithId();
        AnnouncementComment comment2 = 회원2_공지사항댓글.toEntityWithId();
        AnnouncementComment comment3 = 회원_공지사항댓글.toEntityWithId();
        Announcement announcement = 임원1_공지사항.toEntity(announcementComments(comment1, comment2, comment3));

        announcement.deleteComment(comment2.getId(), author(임원1));

        assertThat(extractComments(announcement)).containsExactly(comment1.getComment(), comment3.getComment());
    }

    @DisplayName("작성한 사람보다 접근 권한이 낮으면 댓글을 삭제할 수 없다.")
    @Test
    void deleteCommentWithLowAuthority() {
        AnnouncementComment comment1 = 회장1_공지사항댓글.toEntityWithId();
        AnnouncementComment comment2 = 회장2_공지사항댓글.toEntityWithId();
        Announcement announcement = 임원1_공지사항.toEntity(announcementComments(comment1, comment2));

        assertThatThrownBy(() -> announcement.deleteComment(comment2.getId(), author(임원1)))
                .isInstanceOf(PermissionDeniedException.class);
    }

    private List<String> extractComments(Announcement announcement) {
        return announcement.getNotDeletedComments().stream()
                .map(AnnouncementComment::getComment)
                .collect(Collectors.toList());
    }

    private AnnouncementComments announcementComments(AnnouncementComment... comments) {
        return new AnnouncementComments(Arrays.stream(comments).collect(Collectors.toList()));
    }

    private Author author(MemberFixture memberFixture) {
        return memberFixture.author(Author::new);
    }
}
