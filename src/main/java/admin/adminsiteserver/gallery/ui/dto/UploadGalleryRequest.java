package admin.adminsiteserver.gallery.ui.dto;

import admin.adminsiteserver.gallery.domain.Gallery;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class UploadGalleryRequest extends BaseGalleryRequest {
    public UploadGalleryRequest(String title, String content, List<MultipartFile> images) {
        super(title, content, images);
    }

    public Gallery createGallery(LoginUserInfo loginUserInfo) {
        return Gallery.builder()
                .authorId(loginUserInfo.getUserId())
                .authorName(loginUserInfo.getName())
                .title(getTitle())
                .content(getContent())
                .build();
    }
}
