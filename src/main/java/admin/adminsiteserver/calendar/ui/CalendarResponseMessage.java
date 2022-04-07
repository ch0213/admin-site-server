package admin.adminsiteserver.calendar.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CalendarResponseMessage {
    REGISTER_SUCCESS("일정 등록 성공");

    private final String message;
}
