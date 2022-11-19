package admin.adminsiteserver.calendar.ui;

import admin.adminsiteserver.authentication.domain.LoginMember;
import admin.adminsiteserver.authentication.ui.AuthenticationPrincipal;
import admin.adminsiteserver.calendar.application.CalendarService;
import admin.adminsiteserver.calendar.domain.Author;
import admin.adminsiteserver.calendar.ui.request.CalendarFindRequest;
import admin.adminsiteserver.calendar.ui.request.CalendarRequest;
import admin.adminsiteserver.calendar.ui.response.CalendarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;

    @PostMapping("/calendars")
    public ResponseEntity<Void> create(
            @Valid @RequestBody CalendarRequest request,
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        Long calendarId = calendarService.upload(request, loginMember.toAuthor(Author::new));
        return ResponseEntity.created(URI.create("/calendars/" + calendarId)).build();
    }

    @GetMapping("/calendars")
    public ResponseEntity<List<CalendarResponse>> find(@Valid CalendarFindRequest request) {
        List<CalendarResponse> response = calendarService.findAll(request.getYear(), request.getMonth());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/calendars/{calendarId}")
    public ResponseEntity<CalendarResponse> find(@PathVariable Long calendarId) {
        CalendarResponse response = calendarService.find(calendarId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/calendars/{calendarId}")
    public ResponseEntity<Void> update(
            @PathVariable Long calendarId,
            @Valid @RequestBody CalendarRequest request,
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        calendarService.update(calendarId, request, loginMember.toAuthor(Author::new));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/calendars/{calendarId}")
    public ResponseEntity<Void> delete(@PathVariable Long calendarId, @AuthenticationPrincipal LoginMember loginMember) {
        calendarService.delete(calendarId, loginMember.toAuthor(Author::new));
        return ResponseEntity.noContent().build();
    }
}
