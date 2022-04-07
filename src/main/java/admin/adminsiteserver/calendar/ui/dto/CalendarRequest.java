package admin.adminsiteserver.calendar.ui.dto;

import admin.adminsiteserver.calendar.domain.Calendar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarRequest {

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    public Calendar toCalendar() {
        return new Calendar(title, startDate);
    }
}
