package admin.adminsiteserver.calendar.application;

import admin.adminsiteserver.calendar.domain.Author;
import admin.adminsiteserver.calendar.domain.CalendarQueryRepository;
import admin.adminsiteserver.calendar.exception.CalendarNotFoundException;
import admin.adminsiteserver.calendar.domain.Calendar;
import admin.adminsiteserver.calendar.domain.CalendarRepository;
import admin.adminsiteserver.calendar.ui.request.CalendarRequest;
import admin.adminsiteserver.calendar.ui.response.CalendarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarQueryRepository calendarQueryRepository;
    private final CalendarRepository calendarRepository;

    @Transactional
    public Long upload(CalendarRequest request, Author author) {
        Calendar calendar = calendarRepository.save(request.toEntity(author));
        return calendar.getId();
    }

    @Transactional
    public void update(Long calendarId, CalendarRequest request, Author author) {
        Calendar calendar = findById(calendarId);
        calendar.update(
                request.getTitle(),
                request.getDescription(),
                request.getStartTime(),
                request.getEndTime(),
                author
        );
    }

    @Transactional
    public void delete(Long calendarId, Author author) {
        Calendar calendar = findById(calendarId);
        calendar.delete(author);
    }

    public List<CalendarResponse> findAll(int year, int month) {
        return calendarQueryRepository.findAllByYearAndMonth(year, month)
                .stream().map(CalendarResponse::from)
                .collect(Collectors.toList());
    }

    public CalendarResponse find(Long calendarId) {
        Calendar calendar = findById(calendarId);
        return CalendarResponse.from(calendar);
    }

    private Calendar findById(Long calendarId) {
        return calendarRepository.findByIdAndDeletedIsFalse(calendarId)
                .orElseThrow(CalendarNotFoundException::new);
    }
}
