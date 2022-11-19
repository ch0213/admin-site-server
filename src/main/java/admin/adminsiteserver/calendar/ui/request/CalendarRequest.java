package admin.adminsiteserver.calendar.ui.request;

import admin.adminsiteserver.calendar.domain.Author;
import admin.adminsiteserver.calendar.domain.Calendar;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CalendarRequest {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "설명을 입력해주세요.")
    private String description;

    @NotNull(message = "시작 시간을 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @NotNull(message = "종료 시간을 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    public Calendar toEntity(Author author) {
        return new Calendar(author, title, description, startTime, endTime);
    }
}
