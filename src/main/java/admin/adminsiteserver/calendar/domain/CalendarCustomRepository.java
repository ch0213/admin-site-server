package admin.adminsiteserver.calendar.domain;

import java.util.List;

public interface CalendarCustomRepository {
    List<Calendar> findCalendarByYearAndMonth(int year, int month);
}
