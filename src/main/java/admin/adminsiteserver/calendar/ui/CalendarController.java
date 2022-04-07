package admin.adminsiteserver.calendar.ui;

import admin.adminsiteserver.calendar.application.CalendarService;
import admin.adminsiteserver.calendar.application.dto.CalendarResponse;
import admin.adminsiteserver.calendar.ui.dto.CalendarRequest;
import admin.adminsiteserver.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static admin.adminsiteserver.calendar.ui.CalendarResponseMessage.REGISTER_SUCCESS;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @PostMapping
    public CommonResponse<CalendarResponse> uploadCalendar(@RequestBody CalendarRequest calendarRequest) {
        CalendarResponse calendarResponse = calendarService.uploadCalendar(calendarRequest);
        return CommonResponse.of(calendarResponse, REGISTER_SUCCESS.getMessage());
    }
}
