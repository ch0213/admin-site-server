package admin.adminsiteserver.calendar.domain;

import java.util.Optional;

public interface CalendarRepository {
    Calendar save(Calendar calendar);

    Optional<Calendar> findByIdAndDeletedIsFalse(Long id);
}
