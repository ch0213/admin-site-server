package admin.adminsiteserver.post.announcement.ui;

import lombok.Getter;

@Getter
public enum AnnouncementResponseMessage {
    UPLOAD_SUCCESS("공지사항 등록 성공"),
    UPDATE_SUCCESS("공지사항 수정 성공"),
    DELETE_SUCCESS("공지사항 삭제 성공"),
    FIND_ALL_SUCCESS("공지사항 목록 조회 성공");

    private final String message;

    AnnouncementResponseMessage(String message) {
        this.message = message;
    }
}
