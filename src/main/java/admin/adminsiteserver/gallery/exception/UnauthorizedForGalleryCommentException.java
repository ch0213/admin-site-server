package admin.adminsiteserver.gallery.exception;

import admin.adminsiteserver.common.exception.BaseException;

import java.time.LocalDateTime;

import static admin.adminsiteserver.gallery.exception.GalleryExceptionType.UNAUTHORIZED_FOR_GALLERY_COMMENT;

public class UnauthorizedForGalleryCommentException extends BaseException {
    public UnauthorizedForGalleryCommentException() {
        super(UNAUTHORIZED_FOR_GALLERY_COMMENT.getMessage(), LocalDateTime.now(), UNAUTHORIZED_FOR_GALLERY_COMMENT.getStatus());
    }
}
