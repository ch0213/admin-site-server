package admin.adminsiteserver.calendar.infra;

import admin.adminsiteserver.calendar.domain.Calendar;
import admin.adminsiteserver.calendar.domain.CalendarQueryRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static admin.adminsiteserver.calendar.domain.QCalendar.calendar;

@Repository
@RequiredArgsConstructor
public class JpaCalendarQueryRepository implements CalendarQueryRepository {
    private final JPAQueryFactory factory;

    @Override
    public List<Calendar> findAllByYearAndMonth(int year, int month) {
        return factory.selectFrom(calendar)
                .where(matches(year, month))
                .orderBy(calendar.startTime.asc())
                .fetch();
    }

    @Override
    public List<Calendar> findAllByAuthorId(Long authorId) {
        return factory.selectFrom(calendar)
                .where(calendar.author.memberId.eq(authorId))
                .orderBy(calendar.startTime.asc())
                .fetch();
    }

    private BooleanExpression matches(int year, int month) {
        LocalDateTime min = LocalDateTime.of(LocalDate.of(year, month, 1), LocalTime.MIN);
        LocalDateTime max = LocalDateTime.of(LocalDate.of(year, month, 1).plusMonths(1), LocalTime.MIN);
        return calendar.startTime.before(max)
                .or(calendar.endTime.goe(min))
                .and(calendar.deleted.isFalse());
    }
}
