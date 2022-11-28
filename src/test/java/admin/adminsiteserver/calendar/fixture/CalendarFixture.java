package admin.adminsiteserver.calendar.fixture;

import admin.adminsiteserver.calendar.domain.Author;
import admin.adminsiteserver.calendar.domain.Calendar;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static admin.adminsiteserver.member.fixture.MemberFixture.*;

@RequiredArgsConstructor
public enum CalendarFixture {
    관리자1_일정1(1L, 관리자1.author(Author::new), "개강 총회 - 관리자1", "내일입니다.", LocalDateTime.of(2022, 10, 25, 12, 0), LocalDateTime.of(2022, 11, 2, 17, 0)),
    관리자1_일정2(2L, 관리자1.author(Author::new), "개강 총회 - 관리자1", "내일입니다.", LocalDateTime.of(2022, 10, 27, 12, 0), LocalDateTime.of(2022, 11, 5, 17, 0)),

    관리자2_일정1(3L, 관리자2.author(Author::new), "개강 총회 - 관리자2", "내일입니다.", LocalDateTime.of(2022, 10, 29, 12, 0), LocalDateTime.of(2022, 11, 6, 17, 0)),
    관리자2_일정2(4L, 관리자2.author(Author::new), "개강 총회 - 관리자2", "내일입니다.", LocalDateTime.of(2022, 10, 31, 12, 0), LocalDateTime.of(2022, 11, 7, 17, 0)),

    회장1_일정1(5L, 회장1.author(Author::new), "개강 총회 - 회장1", "내일입니다.", LocalDateTime.of(2022, 11, 1, 12, 0), LocalDateTime.of(2022, 11, 9, 17, 0)),
    회장1_일정2(6L, 회장1.author(Author::new), "개강 총회 - 회장1", "내일입니다.", LocalDateTime.of(2022, 11, 6, 12, 0), LocalDateTime.of(2022, 11, 10, 17, 0)),

    회장2_일정1(7L, 회장2.author(Author::new), "개강 총회 - 회장2", "내일입니다.", LocalDateTime.of(2022, 11, 11, 12, 0), LocalDateTime.of(2022, 11, 23, 17, 0)),
    회장2_일정2(8L, 회장2.author(Author::new), "개강 총회 - 회장2", "내일입니다.", LocalDateTime.of(2022, 11, 16, 12, 0), LocalDateTime.of(2022, 11, 25, 17, 0)),

    임원1_일정1(9L, 임원1.author(Author::new), "개강 총회 - 임원1", "내일입니다.", LocalDateTime.of(2022, 11, 21, 12, 0), LocalDateTime.of(2022, 12, 1, 17, 0)),
    임원1_일정2(10L, 임원1.author(Author::new), "개강 총회 - 임원1", "내일입니다.", LocalDateTime.of(2022, 11, 26, 12, 0), LocalDateTime.of(2022, 12, 3, 17, 0)),

    임원2_일정1(11L, 임원2.author(Author::new), "개강 총회 - 임원2", "내일입니다.", LocalDateTime.of(2022, 11, 28, 12, 0), LocalDateTime.of(2022, 12, 5, 17, 0)),
    임원2_일정2(12L, 임원2.author(Author::new), "개강 총회 - 임원2", "내일입니다.", LocalDateTime.of(2022, 11, 30, 12, 0), LocalDateTime.of(2022, 12, 6, 17, 0));

    public final Long id;
    public final Author author;
    public final String title;
    public final String description;
    public final LocalDateTime startTime;
    public final LocalDateTime endTime;

    public Calendar toEntity() {
        return new Calendar(author, title, description, startTime, endTime);
    }

    public Calendar toEntityWithId() {
        return new Calendar(id, author, title, description, startTime, endTime);
    }
}
