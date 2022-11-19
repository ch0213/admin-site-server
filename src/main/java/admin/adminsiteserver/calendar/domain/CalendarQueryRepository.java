package admin.adminsiteserver.calendar.domain;

import java.util.List;

public interface CalendarQueryRepository {
    List<Calendar> findAllByYearAndMonth(int year, int month);

    List<Calendar> findAllByAuthorId(Long authorId);
}
