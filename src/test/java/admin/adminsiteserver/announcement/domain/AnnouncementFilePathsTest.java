package admin.adminsiteserver.announcement.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static admin.adminsiteserver.announcement.fixture.AnnouncementFilePathFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("공지사항 파일 경로 목록 단위테스트")
class AnnouncementFilePathsTest {
    @DisplayName("공지사항 파일 경로 목록을 생성한다.")
    @Test
    void create() {
        assertDoesNotThrow(() -> new AnnouncementFilePaths());
    }

    @DisplayName("공지사항 파일 경로 목록을 생성한다.")
    @Test
    void createWithFilePaths() {
        List<AnnouncementFilePath> param = announcementFilePaths();

        assertDoesNotThrow(() -> new AnnouncementFilePaths(param));
    }

    @DisplayName("공지사항 파일 경로 목록을 수정한다.")
    @Test
    void update() {
        AnnouncementFilePaths filePaths = new AnnouncementFilePaths(announcementFilePaths());
        AnnouncementFilePath filePath1 = new AnnouncementFilePath("new_file.png1", "new_file_url1");
        AnnouncementFilePath filePath2 = new AnnouncementFilePath("new_file.png2", "new_file_url2");

        filePaths.update(List.of(filePath1, filePath2));

        assertAll(
                () -> assertThat(extractFileNames(filePaths)).containsExactly("new_file.png1", "new_file.png2"),
                () -> assertThat(extractFileUrls(filePaths)).containsExactly("new_file_url1", "new_file_url2")
        );
    }

    @DisplayName("공지사항 파일 경로를 빈 목록으로 수정한다.")
    @Test
    void updateWithEmptyList() {
        List<AnnouncementFilePath> param = announcementFilePaths();
        AnnouncementFilePaths filePaths = new AnnouncementFilePaths(param);

        filePaths.update(Collections.emptyList());

        assertThat(filePaths.getNotDeletedFiles()).isEmpty();
    }

    @DisplayName("모든 공지사항 파일 경로를 삭제한다.")
    @Test
    void deleteAll() {
        AnnouncementFilePaths filePaths = new AnnouncementFilePaths(announcementFilePaths());

        filePaths.deleteAll();

        assertThat(filePaths.getNotDeletedFiles()).isEmpty();
    }

    @DisplayName("삭제되지 않은 공지사항 파일 경로 목록을 조회한다.")
    @Test
    void getNotDeletedFiles() {
        AnnouncementFilePath filePath1 = new AnnouncementFilePath("new_file.png1", "new_file_url1");
        AnnouncementFilePath filePath2 = new AnnouncementFilePath("new_file.png2", "new_file_url2");
        AnnouncementFilePath filePath3 = new AnnouncementFilePath("new_file.png3", "new_file_url3");
        filePath2.delete();

        AnnouncementFilePaths filePaths = new AnnouncementFilePaths(List.of(filePath1, filePath2, filePath3));

        assertAll(
                () -> assertThat(filePaths.getNotDeletedFiles()).hasSize(2),
                () -> assertThat(extractFileNames(filePaths)).containsExactly("new_file.png1", "new_file.png3"),
                () -> assertThat(extractFileUrls(filePaths)).containsExactly("new_file_url1", "new_file_url3")
        );
    }

    private List<String> extractFileNames(AnnouncementFilePaths filePaths) {
        return filePaths.getNotDeletedFiles().stream()
                .map(AnnouncementFilePath::getFileName)
                .collect(Collectors.toList());
    }

    private List<String> extractFileUrls(AnnouncementFilePaths filePaths) {
        return filePaths.getNotDeletedFiles().stream()
                .map(AnnouncementFilePath::getFileUrl)
                .collect(Collectors.toList());
    }
}
