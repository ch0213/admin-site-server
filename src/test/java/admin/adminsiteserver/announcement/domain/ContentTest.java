package admin.adminsiteserver.announcement.domain;

import admin.adminsiteserver.announcement.exception.CommentLengthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("댓글의 내용 단위테스트")
class ContentTest {
    @Test
    void 댓글의_내용을_생성한다() {
        assertDoesNotThrow(() -> new Content("O".repeat(100)));
    }

    @Test
    void 댓글의_내용은_100글자보다_길_수_없다() {
        assertThatThrownBy(() -> new Content("O".repeat(101)))
                .isInstanceOf(CommentLengthException.class);
    }

    @Test
    void 내용이_같은_댓글을_동등하다() {
        Content first = new Content("hello");
        Content second = new Content("hello");
        assertThat(first).isEqualTo(second);
    }

    @Test
    void 내용이_같은_댓글은_같은_hash값을_갖는다() {
        Content first = new Content("hello");
        Content second = new Content("hello");
        assertThat(first).hasSameHashCodeAs(second);
    }
}
