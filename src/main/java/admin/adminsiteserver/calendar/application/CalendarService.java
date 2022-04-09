package admin.adminsiteserver.calendar.application;

import admin.adminsiteserver.calendar.application.dto.CalendarResponse;
import admin.adminsiteserver.calendar.domain.Calendar;
import admin.adminsiteserver.calendar.domain.CalendarRepository;
import admin.adminsiteserver.calendar.exception.NotExistCalendarException;
import admin.adminsiteserver.calendar.ui.dto.CalendarRequest;
import admin.adminsiteserver.calendar.ui.dto.InquireCalendarRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        calendar.updateTitleAndStartDate(calendarRequest.getTitle(), calendarRequest.getStartDate());
        return CalendarResponse.from(calendar);
    }

    @Transactional
    public void deleteCalendar(Long calendarId) {
        Calendar calendar = calendarRepository.findById(calendarId)
                .orElseThrow(NotExistCalendarException::new);
        calendarRepository.delete(calendar);
    }

    public List<CalendarResponse> findCalendars(InquireCalendarRequest request) {
        return calendarRepository.findCalendarByYearAndMonth(request.getYear())
                .stream().map(CalendarResponse::from)
                .collect(Collectors.toList());
    }
}
