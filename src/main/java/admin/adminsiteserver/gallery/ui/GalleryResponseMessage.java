package admin.adminsiteserver.gallery.ui;

import lombok.Getter;

@Getter
public enum GalleryResponseMessage {
    GALLERY_UPLOAD_SUCCESS("갤러리 등록 성공"),
    GALLERY_UPDATE_SUCCESS("갤러리 수정 성공"),
    GALLERY_DELETE_SUCCESS("갤러리 삭제 성공"),
    GALLERY_FIND_SUCCESS("갤러리 조회 성공"),
    GALLERY_FIND_ALL_SUCCESS("갤러리 목록 조회 성공"),
    COMMENT_UPLOAD_SUCCESS("댓글 등록 성공"),
    COMMENT_UPDATE_SUCCESS("댓글 수정 성공"),
    COMMENT_DELETE_SUCCESS("댓글 삭제 성공"),;

    private final String message;

    GalleryResponseMessage(String message) {
        this.message = message;
    }
}
