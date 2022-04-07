package admin.adminsiteserver.calendar.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CalendarResponseMessage {
    CALENDAR_REGISTER_SUCCESS("일정 등록 성공"),
    CALENDAR_UPDATE_SUCCESS("일정 수정 성공"),
    CALENDAR_DELETE_SUCCESS("일정 삭제 성공"),
    CALENDAR_INQUIRE_SUCCESS("일정 조회 성공"),
    CALENDAR_LIST_INQUIRE_SUCCESS("일정목록 조회 성공");

    private final String message;
}
