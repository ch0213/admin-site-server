package admin.adminsiteserver.announcement.domain;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AnnouncementTest {
    Announcement announcement;

    @BeforeEach
    void init() {
        announcement = Announcement.builder()
                .authorId("testUser")
                .authorName("테스트 유저")
                .title("테스트 게시물입니다.")
                .content("테스트 게시물의 내용입니다.")
                .build();
    }

    @DisplayName("공지사항 제목, 내용 변경 테스트")
    @Test
    void updateTitleAndContentTest() {
        announcement.updateTitleAndContent("제목 변경", "내용 변경");
        assertThat(announcement.getTitle()).isEqualTo("제목 변경");
        assertThat(announcement.getContent()).isEqualTo("내용 변경");
    }

    @DisplayName("파일 등록 테스트")
    @Test
    void uploadFile() {
        for (int i = 0; i < 5; i++) {
            announcement.getFiles().add(new AnnouncementFilePath("테스트 파일명" + i, "테스트 파일 URL" + i));
        }

        assertThat(announcement.getFiles()).hasSize(5);
    }

    @DisplayName("게시물에서 파일 삭제하는 경우")
    @Test
    void deleteFilesNormalCase() {
        for (int i = 0; i < 5; i++) {
            announcement.getFiles().add(new AnnouncementFilePath("테스트 파일명" + i, "테스트 파일 URL" + i));
        }
        List<FilePathDto> deleteFilePathDots = new ArrayList<>();
        for (int i = 0; i < 5; i += 2) {
            deleteFilePathDots.add(new FilePathDto("테스트 파일명" + i, "테스트 파일 URL" + i));
        }

        announcement.deleteFiles(deleteFilePathDots);

        assertThat(announcement.getFiles()).hasSize(2);
    }

    @DisplayName("게시물에 파일이 없을 때 삭제하는 경우")
    @Test
    void deleteFiles() {
        List<FilePathDto> filePathDtos = new ArrayList<>();
        filePathDtos.add(new FilePathDto("테스트 파일명", "테스트 파일 URL"));

        announcement.deleteFiles(filePathDtos);

        assertThat(announcement.getFiles()).hasSize(0);
    }
}