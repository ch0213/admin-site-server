package admin.adminsiteserver.announcement.domain;

import admin.adminsiteserver.common.exception.PermissionDeniedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static admin.adminsiteserver.announcement.fixture.AnnouncementCommentFixture.*;
import static admin.adminsiteserver.announcement.util.MemberFixtureConverter.author;
import static admin.adminsiteserver.member.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("공지사항 댓글 목록 단위테스트")
class AnnouncementCommentsTest {
    @DisplayName("공지사항에 댓글을 작성한다.")
    @Test
    void add() {
        AnnouncementComments comments = new AnnouncementComments();

        comments.add("안녕하세요", author(회원1));
        comments.add("반갑습니다", author(회원2));

        assertThat(extractComments(comments)).containsExactly("안녕하세요", "반갑습니다");
    }

    @DisplayName("공지사항의 댓글을 변경한다.")
    @Test
    void update() {
        AnnouncementComment comment = 회원_공지사항댓글.toEntityWithId();
        AnnouncementComment comment1 = 회원1_공지사항댓글.toEntityWithId();
        AnnouncementComments comments = new AnnouncementComments(List.of(comment, comment1));

        comments.update(comment.getId(), "변경되었습니다", author(회원));

        assertThat(extractComments(comments)).containsExactly("변경되었습니다", 회원1_공지사항댓글.getComment());
    }

    @DisplayName("다른 사람의 댓글을 변경할 수 없다.")
    @Test
    void updateOther() {
        AnnouncementComment comment = 회원_공지사항댓글.toEntityWithId();
        AnnouncementComment comment1 = 회원1_공지사항댓글.toEntityWithId();
        AnnouncementComments comments = new AnnouncementComments(List.of(comment, comment1));

        assertThatThrownBy(() -> comments.update(comment.getId(), "변경되었습니다", author(회원1)))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @DisplayName("작성한 사람보다 접근 권한이 높으면 공지사항 댓글을 수정할 수 있다.")
    @Test
    void updateWithHighAuthority() {
        AnnouncementComment comment = 회원_공지사항댓글.toEntityWithId();
        AnnouncementComment comment1 = 회원1_공지사항댓글.toEntityWithId();
        AnnouncementComments comments = new AnnouncementComments(List.of(comment, comment1));

        comments.update(comment.getId(), "변경되었습니다", author(임원1));

        assertThat(extractComments(comments)).containsExactly("변경되었습니다", 회원1_공지사항댓글.getComment());
    }

    @DisplayName("작성한 사람보다 접근 권한이 낮으면 공지사항 댓글을 수정할 수 없다.")
    @Test
    void updateWithLowAuthority() {
        AnnouncementComment comment = 회장1_공지사항댓글.toEntityWithId();
        AnnouncementComment comment1 = 회장2_공지사항댓글.toEntityWithId();
        AnnouncementComments comments = new AnnouncementComments(List.of(comment, comment1));

        assertThatThrownBy(() -> comments.update(comment.getId(), "변경되었습니다", author(임원1)))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @DisplayName("공지사항의 댓글을 삭제한다.")
    @Test
    void delete() {
        AnnouncementComment comment = 회원_공지사항댓글.toEntityWithId();
        AnnouncementComment comment1 = 회원1_공지사항댓글.toEntityWithId();
        AnnouncementComments comments = new AnnouncementComments(List.of(comment, comment1));

        comments.delete(comment.getId(), author(회원));

        assertThat(extractComments(comments)).containsExactly(회원1_공지사항댓글.getComment());
    }

    @DisplayName("다른 사람의 댓글을 변경할 수 없다.")
    @Test
    void deleteOther() {
        AnnouncementComment comment = 회원_공지사항댓글.toEntityWithId();
        AnnouncementComment comment1 = 회원1_공지사항댓글.toEntityWithId();
        AnnouncementComments comments = new AnnouncementComments(List.of(comment, comment1));

        assertThatThrownBy(() -> comments.delete(comment.getId(), author(회원1)))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @DisplayName("작성한 사람보다 접근 권한이 높으면 공지사항 댓글을 수정할 수 있다.")
    @Test
    void deleteWithHighAuthority() {
        AnnouncementComment comment = 회원_공지사항댓글.toEntityWithId();
        AnnouncementComment comment1 = 회원1_공지사항댓글.toEntityWithId();
        AnnouncementComments comments = new AnnouncementComments(List.of(comment, comment1));

        comments.delete(comment.getId(), author(임원1));

        assertThat(extractComments(comments)).containsExactly(회원1_공지사항댓글.getComment());
    }

    @DisplayName("작성한 사람보다 접근 권한이 낮으면 공지사항 댓글을 수정할 수 없다.")
    @Test
    void deleteWithLowAuthority() {
        AnnouncementComment comment = 회장1_공지사항댓글.toEntityWithId();
        AnnouncementComment comment1 = 회장2_공지사항댓글.toEntityWithId();
        AnnouncementComments comments = new AnnouncementComments(List.of(comment, comment1));

        assertThatThrownBy(() -> comments.delete(comment.getId(), author(임원1)))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @DisplayName("모든 댓글을 삭제한다.")
    @Test
    void deleteAll() {
        AnnouncementComment comment = 회원_공지사항댓글.toEntityWithId();
        AnnouncementComment comment1 = 회원1_공지사항댓글.toEntityWithId();
        AnnouncementComments comments = new AnnouncementComments(List.of(comment, comment1));

        comments.deleteAll();

        assertThat(comments.getNotDeletedComments()).isEmpty();
    }

    private List<String> extractComments(AnnouncementComments comments) {
        return comments.getNotDeletedComments().stream()
                .map(AnnouncementComment::getComment)
                .collect(Collectors.toList());
    }
}
