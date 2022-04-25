package admin.adminsiteserver.announcement.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AnnouncementCommentTest {

    @DisplayName("공지사항 댓글 내용 변경 테스트")
    @Test
    void updateCommentTest() {
        AnnouncementComment comment = new AnnouncementComment("testUser", "테스트유저", "댓글입니다.");

        comment.updateComment("댓글 수정했습니다.");

        Assertions.assertThat(comment.getComment()).isEqualTo("댓글 수정했습니다.");
    }
}