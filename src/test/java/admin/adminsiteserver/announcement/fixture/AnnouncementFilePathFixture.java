package admin.adminsiteserver.announcement.fixture;

import admin.adminsiteserver.announcement.domain.AnnouncementFilePath;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum AnnouncementFilePathFixture {
    공지사항_첨부파일1(1L, "첨부파일1", "fileUrl1"),
    공지사항_첨부파일2(2L, "첨부파일2", "fileUrl2"),
    공지사항_첨부파일3(3L, "첨부파일3", "fileUrl3");

    private final Long id;
    private final String fileName;
    private final String fileUrl;

    public AnnouncementFilePath toEntity() {
        return new AnnouncementFilePath(fileName, fileUrl);
    }

    public AnnouncementFilePath toEntityWithId() {
        return new AnnouncementFilePath(id, fileName, fileUrl);
    }

    public static List<AnnouncementFilePath> announcementFilePaths() {
        return List.of(공지사항_첨부파일1.toEntityWithId(), 공지사항_첨부파일2.toEntityWithId(), 공지사항_첨부파일3.toEntityWithId());
    }
}
