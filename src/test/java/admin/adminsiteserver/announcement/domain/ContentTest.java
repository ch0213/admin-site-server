package admin.adminsiteserver.announcement.domain;

import admin.adminsiteserver.announcement.exception.CommentLengthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("댓글의 내용 단위테스트")
class ContentTest {
    @DisplayName("댓글의 내용을 생성한다.")
    @Test
    void create() {
        assertDoesNotThrow(() -> new Content("O".repeat(100)));
    }

    @DisplayName("댓글의 내용은 100글자보다 길 수 없다.")
    @Test
    void createLongComment() {
        assertThatThrownBy(() -> new Content("O".repeat(101)))
                .isInstanceOf(CommentLengthException.class);
    }
}
