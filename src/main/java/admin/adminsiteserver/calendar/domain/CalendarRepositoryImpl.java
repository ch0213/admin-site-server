package admin.adminsiteserver.calendar.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static admin.adminsiteserver.calendar.domain.QCalendar.calendar;

@RequiredArgsConstructor
public class CalendarRepositoryImpl implements CalendarCustomRepository{

    private final JPAQueryFactory factory;

    @Override
    public List<Calendar> findCalendarByYearAndMonth(int year) {
        LocalDate lowerBound = LocalDate.of(year, 1, 1).minusDays(1);
        LocalDate upperBound = LocalDate.of(year, 12, 31).plusDays(1);
        return factory.selectFrom(calendar)
                .where(calendar.startDate.after(lowerBound), calendar.startDate.before(upperBound))
                .orderBy(calendar.startDate.asc())
                .fetch();
    }
}
