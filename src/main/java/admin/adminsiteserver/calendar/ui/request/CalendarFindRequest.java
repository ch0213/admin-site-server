package admin.adminsiteserver.calendar.ui.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class CalendarFindRequest {
    @Range(min = 0, max = 9999, message = "연도는 0 ~ 9999여야 합니다.")
    @NotNull(message = "연도를 입력해주세요.")
    private Integer year;

    @Range(min = 1, max = 12, message = "월은 1 ~ 12여야 합니다.")
    @NotNull(message = "월을 입력해주세요.")
    private Integer month;
}
