package admin.adminsiteserver.announcement.domain;

import admin.adminsiteserver.common.exception.PermissionDeniedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static admin.adminsiteserver.member.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("공지사항 댓글 단위테스트")
class AnnouncementCommentTest {
    @DisplayName("공지사항 댓글을 생성한다.")
    @Test
    void create() {
        assertDoesNotThrow(() -> new AnnouncementComment("안녕하세요.", 회원.getAuthor()));
    }

    @DisplayName("공지사항의 댓글을 수정한다.")
    @Test
    void update() {
        AnnouncementComment comment = new AnnouncementComment("안녕하세요.", 회원.getAuthor());

        comment.update("반갑습니다.", 회원.getAuthor());

        assertThat(comment.getComment()).isEqualTo("반갑습니다.");
    }

    @DisplayName("다른 사람이 작성한 공지사항 댓글을 수정할 수 없다.")
    @Test
    void updateOther() {
        AnnouncementComment comment = new AnnouncementComment("안녕하세요.", 회원1.getAuthor());

        assertThatThrownBy(() -> comment.update("반갑습니다.", 회원2.getAuthor()))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @DisplayName("작성한 사람보다 접근 권한이 높을 경우 공지사항 댓글을 수정할 수 있다.")
    @Test
    void updateWithHighAuthority() {
        AnnouncementComment comment = new AnnouncementComment("안녕하세요.", 회원.getAuthor());

        comment.update("반갑습니다.", 임원1.getAuthor());

        assertThat(comment.getComment()).isEqualTo("반갑습니다.");
    }

    @DisplayName("공지사항의 댓글을 삭제한다.")
    @Test
    void delete() {
        AnnouncementComment comment = new AnnouncementComment("안녕하세요.", 회원1.getAuthor());

        comment.delete(회원1.getAuthor());

        assertThat(comment.isDeleted()).isTrue();
    }

    @DisplayName("다른 사람이 작성한 공지사항 댓글을 삭제할 수 없다.")
    @Test
    void deleteOther() {
        AnnouncementComment comment = new AnnouncementComment("안녕하세요.", 회원1.getAuthor());

        assertThatThrownBy(() -> comment.delete(회원2.getAuthor()))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @DisplayName("작성한 사람보다 접근 권한이 높을 경우 공지사항 댓글을 삭제할 수 있다.")
    @Test
    void deleteWithHighAuthority() {
        AnnouncementComment comment = new AnnouncementComment("안녕하세요.", 회원.getAuthor());

        comment.delete(임원1.getAuthor());

        assertThat(comment.isDeleted()).isTrue();
    }

    @DisplayName("공지사항의 댓글을 강제로 삭제한다.")
    @Test
    void forceDelete() {
        AnnouncementComment comment = new AnnouncementComment("안녕하세요.", 관리자1.getAuthor());

        comment.forceDelete();

        assertThat(comment.isDeleted()).isTrue();
    }
}
