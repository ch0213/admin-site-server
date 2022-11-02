package admin.adminsiteserver.announcement.fixture;

import admin.adminsiteserver.announcement.domain.Announcement;
import admin.adminsiteserver.announcement.domain.AnnouncementComments;
import admin.adminsiteserver.announcement.domain.AnnouncementFilePaths;
import admin.adminsiteserver.announcement.domain.Author;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;

import static admin.adminsiteserver.announcement.util.MemberFixtureConverter.author;
import static admin.adminsiteserver.member.fixture.MemberFixture.*;

@Getter
@RequiredArgsConstructor
public enum AnnouncementFixture {
    관리자1_공지사항(1L, author(관리자1), "안녕하세요 관리자1의 공지사항입니다.", "잘부탁드립니다 - 괸리자1"),
    관리자2_공지사항(2L, author(관리자2), "안녕하세요 관리자2의 공지사항입니다.", "잘부탁드립니다 - 괸리자2"),

    회장1_공지사항(3L, author(회장1), "안녕하세요 회장1의 공지사항입니다.", "잘부탁드립니다 - 회장1"),
    회장2_공지사항(4L, author(회장2), "안녕하세요 회장2의 공지사항입니다.", "잘부탁드립니다 - 회장2"),

    임원1_공지사항(5L, author(임원1), "안녕하세요 임원1의 공지사항입니다.", "잘부탁드립니다 - 임원1"),
    임원2_공지사항(6L, author(관리자1), "안녕하세요 임원2의 공지사항입니다.", "잘부탁드립니다 - 임원2"),

    회원1_공지사항(7L, author(회원1), "안녕하세요 회원1의 공지사항입니다.", "잘부탁드립니다 - 회원1"),
    회원2_공지사항(8L, author(회원2), "안녕하세요 회원2의 공지사항입니다.", "잘부탁드립니다 - 회원2"),
    회원_공지사항(9L, author(회원), "안녕하세요 회원의 공지사항입니다.", "잘부탁드립니다 - 회원");

    private final Long id;

    private final Author author;

    private final String title;

    private final String content;

    public Announcement toEntity() {
        return new Announcement(author, title, content, Collections.emptyList());
    }

    public Announcement toEntity(AnnouncementFilePaths filePaths) {
        return new Announcement(1L, author, title, content, filePaths, new AnnouncementComments(Collections.emptyList()));
    }

    public Announcement toEntity(AnnouncementComments comments) {
        return new Announcement(1L, author, title, content, new AnnouncementFilePaths(Collections.emptyList()), comments);
    }

    public Announcement toEntity(AnnouncementFilePaths filePaths, AnnouncementComments comments) {
        return new Announcement(1L, author, title, content, filePaths, comments);
    }

    public Announcement toEntityWithId() {
        return new Announcement(id, author, title, content, new AnnouncementFilePaths(Collections.emptyList()));
    }
}
