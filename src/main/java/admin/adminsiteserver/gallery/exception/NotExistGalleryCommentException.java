package admin.adminsiteserver.gallery.exception;

import admin.adminsiteserver.common.exception.BaseException;

import java.time.LocalDateTime;

import static admin.adminsiteserver.gallery.exception.GalleryExceptionType.NOT_EXIST_GALLERY_COMMENT;

public class NotExistGalleryCommentException extends BaseException {
    public NotExistGalleryCommentException() {
        super(NOT_EXIST_GALLERY_COMMENT.getMessage(), LocalDateTime.now(), NOT_EXIST_GALLERY_COMMENT.getStatus());
    }
}
