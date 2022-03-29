package admin.adminsiteserver.post.announcement.ui;

import lombok.Getter;

@Getter
public enum AnnouncementResponseMessage {
    UPLOAD_SUCCESS("공지사항 등록 성공");

    private final String message;

    AnnouncementResponseMessage(String message) {
        this.message = message;
    }
}
