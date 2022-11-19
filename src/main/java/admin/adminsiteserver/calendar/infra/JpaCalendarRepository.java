package admin.adminsiteserver.calendar.infra;

import admin.adminsiteserver.calendar.domain.Calendar;
import admin.adminsiteserver.calendar.domain.CalendarRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCalendarRepository extends CalendarRepository, JpaRepository<Calendar, Long> {
}
