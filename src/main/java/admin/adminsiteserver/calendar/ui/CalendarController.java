package admin.adminsiteserver.calendar.ui;

import admin.adminsiteserver.calendar.application.CalendarService;
import admin.adminsiteserver.calendar.application.dto.CalendarResponse;
import admin.adminsiteserver.calendar.ui.dto.CalendarRequest;
import admin.adminsiteserver.calendar.ui.dto.InquireCalendarRequest;
import admin.adminsiteserver.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static admin.adminsiteserver.calendar.ui.CalendarResponseMessage.*;

@RestController
@RequestMapping("/calendars")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @PostMapping
    public CommonResponse<CalendarResponse> uploadCalendar(@Valid @RequestBody CalendarRequest calendarRequest) {
        CalendarResponse calendarResponse = calendarService.uploadCalendar(calendarRequest);
        return CommonResponse.of(calendarResponse, CALENDAR_REGISTER_SUCCESS.getMessage());
    }

    @PutMapping("/{calendarId}")
    public CommonResponse<CalendarResponse> updateCalendar(
            @Valid @RequestBody CalendarRequest calendarRequest,
            @PathVariable Long calendarId
    ) {
        CalendarResponse calendarResponse = calendarService.updateCalendar(calendarRequest, calendarId);
        return CommonResponse.of(calendarResponse, CALENDAR_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{calendarId}")
    public CommonResponse<Void> deleteCalendar(@PathVariable Long calendarId) {
        calendarService.deleteCalendar(calendarId);
        return CommonResponse.from(CALENDAR_DELETE_SUCCESS.getMessage());
    }

    @GetMapping
    public CommonResponse<List<CalendarResponse>> inquireCalendar(@Valid InquireCalendarRequest request) {
        return CommonResponse.of(calendarService.findCalendars(request), CALENDAR_LIST_INQUIRE_SUCCESS.getMessage());
    }
}
