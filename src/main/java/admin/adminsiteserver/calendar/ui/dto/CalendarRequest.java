package admin.adminsiteserver.calendar.ui.dto;

import admin.adminsiteserver.calendar.domain.Calendar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotNull(message = "시작일을 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    public Calendar toCalendar() {
        return new Calendar(title, startDate);
    }
}
