package admin.adminsiteserver.calendar.application;

import admin.adminsiteserver.calendar.application.dto.CalendarResponse;
import admin.adminsiteserver.calendar.domain.Calendar;
import admin.adminsiteserver.calendar.domain.CalendarRepository;
import admin.adminsiteserver.calendar.exception.NotExistCalendarException;
import admin.adminsiteserver.calendar.ui.dto.CalendarRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarService {

    private final CalendarRepository calendarRepository;

    @Transactional
    public CalendarResponse uploadCalendar(CalendarRequest calendarRequest) {
        return CalendarResponse.from(calendarRepository.save(calendarRequest.toCalendar()));
    }

    @Transactional
    public CalendarResponse updateCalendar(CalendarRequest calendarRequest, Long calendarId) {
        Calendar calendar = calendarRepository.findById(calendarId)
                .orElseThrow(NotExistCalendarException::new);
        calendar.updateTitleAndStartDate(calendarRequest.getTitle(), calendar.getStartDate());
        return CalendarResponse.from(calendar);
    }
}
