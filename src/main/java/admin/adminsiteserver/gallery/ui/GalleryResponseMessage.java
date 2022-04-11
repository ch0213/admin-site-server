package admin.adminsiteserver.gallery.ui;

import lombok.Getter;

@Getter
public enum GalleryResponseMessage {
    GALLERY_UPLOAD_SUCCESS("공지사항 등록 성공"),
    GALLERY_UPDATE_SUCCESS("공지사항 수정 성공"),
    GALLERY_DELETE_SUCCESS("공지사항 삭제 성공"),
    GALLERY_FIND_SUCCESS("공지사항 조회 성공"),
    GALLERY_FIND_ALL_SUCCESS("공지사항 목록 조회 성공");

    private final String message;

    GalleryResponseMessage(String message) {
        this.message = message;
    }
}
