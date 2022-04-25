package admin.adminsiteserver.calendar.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class InquireCalendarRequest {
    @Range(min = 0, max = 9999, message = "연도는 0 ~ 9999여야 합니다.")
    @NotNull(message = "연도를 입력해주세요.")
    private Integer year;
}
