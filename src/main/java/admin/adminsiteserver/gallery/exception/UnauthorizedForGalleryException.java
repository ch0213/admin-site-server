package admin.adminsiteserver.gallery.exception;

import admin.adminsiteserver.common.exception.BaseException;

import java.time.LocalDateTime;

import static admin.adminsiteserver.gallery.exception.GalleryExceptionType.UNAUTHORIZED_FOR_GALLERY;

public class UnauthorizedForGalleryException extends BaseException {
    public UnauthorizedForGalleryException() {
        super(UNAUTHORIZED_FOR_GALLERY.getMessage(), LocalDateTime.now(), UNAUTHORIZED_FOR_GALLERY.getStatus());
    }
}
