package admin.adminsiteserver.calendar.application.dto;

import admin.adminsiteserver.calendar.domain.Calendar;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CalendarResponse {

    private Long id;
    private String title;
    private LocalDate startDate;

    public static CalendarResponse from(Calendar calendar) {
        return new CalendarResponse(calendar.getId(), calendar.getTitle(), calendar.getStartDate());
    }
}
