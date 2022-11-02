package admin.adminsiteserver.announcement.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("공지사항 파일 경로 단위테스트")
class AnnouncementFilePathTest {
    @DisplayName("공지사항 파일 경로를 생성한다.")
    @Test
    void create() {
        assertDoesNotThrow(() -> new AnnouncementFilePath("파일명.png", "파일URL"));
    }

    @DisplayName("공지사항 파일 경로를 삭제한다.")
    @Test
    void delete() {
        AnnouncementFilePath filePath = new AnnouncementFilePath("파일명.png", "파일URL");

        filePath.delete();

        assertAll(
                () -> assertThat(filePath.isDeleted()).isTrue(),
                () -> assertThat(filePath.notDeleted()).isFalse()
        );
    }
}
