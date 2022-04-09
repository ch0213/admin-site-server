package admin.adminsiteserver.gallery.exception;

import admin.adminsiteserver.common.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

import static admin.adminsiteserver.gallery.exception.GalleryExceptionType.NOT_EXIST_GALLERY;

@Getter
public class NotExistGalleryException extends BaseException {
    public NotExistGalleryException() {
        super(NOT_EXIST_GALLERY.getMessage(), LocalDateTime.now(), NOT_EXIST_GALLERY.getStatus());
    }
}
