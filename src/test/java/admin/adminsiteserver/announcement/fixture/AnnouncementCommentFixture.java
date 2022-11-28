package admin.adminsiteserver.announcement.fixture;

import admin.adminsiteserver.announcement.domain.AnnouncementComment;
import admin.adminsiteserver.announcement.domain.Author;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static admin.adminsiteserver.member.fixture.MemberFixture.*;

@Getter
@RequiredArgsConstructor
public enum AnnouncementCommentFixture {
    관리자1_공지사항댓글(1L, "안녕하세요 관리자1입니다.", 관리자1.author(Author::new)),
    관리자2_공지사항댓글(2L, "안녕하세요 관리자2입니다.", 관리자2.author(Author::new)),

    회장1_공지사항댓글(3L, "안녕하세요 회장1입니다.", 회장1.author(Author::new)),
    회장2_공지사항댓글(4L, "안녕하세요 회장2입니다.", 회장2.author(Author::new)),

    임원1_공지사항댓글(5L, "안녕하세요 임원1입니다.", 임원1.author(Author::new)),
    임원2_공지사항댓글(6L, "안녕하세요 임원2입니다.", 임원2.author(Author::new)),

    회원1_공지사항댓글(7L, "안녕하세요 회원1입니다.", 회원1.author(Author::new)),
    회원2_공지사항댓글(8L, "안녕하세요 회원2입니다.", 회원2.author(Author::new)),
    회원_공지사항댓글(9L, "안녕하세요 회원입니다.", 회원.author(Author::new));

    private final Long id;
    private final String comment;
    private final Author author;

    public AnnouncementComment toEntity() {
        return new AnnouncementComment(comment, author);
    }

    public AnnouncementComment toEntityWithId() {
        return new AnnouncementComment(id, comment, author);
    }
}
