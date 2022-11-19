package admin.adminsiteserver.calendar.ui.response;

import admin.adminsiteserver.calendar.domain.Calendar;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CalendarResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;

    public CalendarResponse(Long id, String title, String description, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
    }

    public static CalendarResponse from(Calendar calendar) {
        return new CalendarResponse(
                calendar.getId(),
                calendar.getTitle(),
                calendar.getDescription(),
                calendar.getStartTime(),
                calendar.getEndTime()
        );
    }
}
