package admin.adminsiteserver.levelup.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LevelUpResponseMessage {
    LEVEL_UP_REGISTER_SUCCESS("권한 업그레이드 신청 성공");

    private final String message;
}
