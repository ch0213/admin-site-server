package admin.adminsiteserver.calendar.ui;

import admin.adminsiteserver.calendar.application.CalendarService;
import admin.adminsiteserver.calendar.application.dto.CalendarResponse;
import admin.adminsiteserver.calendar.ui.dto.CalendarRequest;
import admin.adminsiteserver.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static admin.adminsiteserver.calendar.ui.CalendarResponseMessage.*;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @PostMapping
    public CommonResponse<CalendarResponse> uploadCalendar(@RequestBody CalendarRequest calendarRequest) {
        CalendarResponse calendarResponse = calendarService.uploadCalendar(calendarRequest);
        return CommonResponse.of(calendarResponse, CALENDAR_REGISTER_SUCCESS.getMessage());
    }

    @PutMapping("/{calendarId}")
    public CommonResponse<CalendarResponse> updateCalendar(
            @RequestBody CalendarRequest calendarRequest,
            @PathVariable Long calendarId
    ) {
        CalendarResponse calendarResponse = calendarService.updateCalendar(calendarRequest, calendarId);
        return CommonResponse.of(calendarResponse, CALENDAR_UPDATE_SUCCESS.getMessage());
    }
}
