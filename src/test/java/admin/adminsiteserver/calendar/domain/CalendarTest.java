package admin.adminsiteserver.calendar.domain;

import admin.adminsiteserver.calendar.exception.CalendarPeriodException;
import admin.adminsiteserver.common.exception.PermissionDeniedException;
import admin.adminsiteserver.member.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static admin.adminsiteserver.calendar.fixture.CalendarFixture.관리자1_일정1;
import static admin.adminsiteserver.calendar.fixture.CalendarFixture.임원1_일정1;
import static admin.adminsiteserver.common.domain.RoleType.ADMIN;
import static admin.adminsiteserver.member.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("일정 단위테스트")
class CalendarTest {
    @Test
    void 일정을_생성한다() {
        assertDoesNotThrow(() -> new Calendar(author(관리자1), "일정", "설명", LocalDateTime.MIN, LocalDateTime.MAX));
    }

    @Test
    void 종료가_시작보다_빠르면_예외가_발생한다() {
        assertThatThrownBy(() -> new Calendar(author(관리자1), "일정", "설명", LocalDateTime.MAX, LocalDateTime.MIN))
                .isInstanceOf(CalendarPeriodException.class);
    }

    @Test
    void 일정을_수정한다() {
        Calendar calendar = 관리자1_일정1.toEntity();

        calendar.update("변경 후", "변경 후", LocalDateTime.MIN, LocalDateTime.MAX, author(관리자1));

        assertAll(
                () -> assertThat(calendar.getTitle()).isEqualTo("변경 후"),
                () -> assertThat(calendar.getTitle()).isEqualTo("변경 후"),
                () -> assertThat(calendar.getStartTime()).isEqualTo(LocalDateTime.MIN),
                () -> assertThat(calendar.getEndTime()).isEqualTo(LocalDateTime.MAX)
        );
    }

    @Test
    void 변경된_일정의_종료가_시작보다_빠르면_예외가_발생한다() {
        Calendar calendar = 관리자1_일정1.toEntity();

        assertThatThrownBy(() -> calendar.update("변경 후", "변경 후", LocalDateTime.MAX, LocalDateTime.MIN, author(관리자1)))
                .isInstanceOf(CalendarPeriodException.class);
    }

    @Test
    void 다른_사람이_작성한_일정을_수정할_수_없다() {
        Calendar calendar = 관리자1_일정1.toEntity();

        assertThatThrownBy(() -> calendar.update("변경 후", "변경 후", LocalDateTime.MIN, LocalDateTime.MAX, author(관리자2)))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @Test
    void 작성한_사람보다_접근_권한이_높으면_일정을_수정할_수_있다() {
        Calendar calendar = 임원1_일정1.toEntity();

        calendar.update("변경 후", "변경 후", LocalDateTime.MIN, LocalDateTime.MAX, author(관리자1));

        assertAll(
                () -> assertThat(calendar.getTitle()).isEqualTo("변경 후"),
                () -> assertThat(calendar.getTitle()).isEqualTo("변경 후"),
                () -> assertThat(calendar.getStartTime()).isEqualTo(LocalDateTime.MIN),
                () -> assertThat(calendar.getEndTime()).isEqualTo(LocalDateTime.MAX)
        );
    }

    @Test
    void 작성한_사람보다_접근_권한이_낮으면_일정을_수정할_수_없다() {
        Calendar calendar = 관리자1_일정1.toEntity();

        assertThatThrownBy(() -> calendar.update("변경 후", "변경 후", LocalDateTime.MIN, LocalDateTime.MAX, author(임원1)))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @Test
    void 일정을_삭제한다() {
        Calendar calendar = 관리자1_일정1.toEntity();

        calendar.delete(author(관리자1));

        assertThat(calendar.isDeleted()).isTrue();
    }

    @Test
    void 다른_사람이_작성한_일정을_삭제할_수_없다() {
        Calendar calendar = 관리자1_일정1.toEntity();

        assertThatThrownBy(() -> calendar.delete(author(관리자2)))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @Test
    void 작성한_사람보다_접근_권한이_높으면_일정을_삭제할_수_있다() {
        Calendar calendar = 임원1_일정1.toEntity();

        calendar.delete(author(관리자1));

        assertThat(calendar.isDeleted()).isTrue();
    }

    @Test
    void 작성한_사람보다_접근_권한이_낮으면_일정을_삭제할_수_없다() {
        Calendar calendar = 관리자1_일정1.toEntity();

        assertThatThrownBy(() -> calendar.delete(author(임원1)))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @Test
    void 일정의_작성자를_수정한다() {
        Calendar calendar = 관리자1_일정1.toEntity();

        calendar.exchange(new Author(관리자1.getId(), "변경 후 이름", "202012345", ADMIN));

        assertThat(calendar.getAuthor().getName())
                .isEqualTo("변경 후 이름");
    }

    @Test
    void 일정의_작성자와_ID가_다르면_수정하지_않는다() {
        Calendar calendar = 관리자1_일정1.toEntity();

        calendar.exchange(new Author(관리자2.getId(), "변경 후 이름", "202012345", ADMIN));

        assertThat(calendar.getAuthor().getName())
                .isNotEqualTo("변경 후 이름");
    }

    private Author author(MemberFixture memberFixture) {
        return memberFixture.author(Author::new);
    }
}
