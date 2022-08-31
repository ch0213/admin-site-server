package admin.adminsiteserver.gallery.ui.dto;

import admin.adminsiteserver.aws.dto.response.FilePath;
import admin.adminsiteserver.gallery.domain.Gallery;
import admin.adminsiteserver.authentication.ui.LoginUserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UploadGalleryRequest extends BaseGalleryRequest {
    public UploadGalleryRequest(String title, String content, List<FilePath> files) {
        super(title, content, files);
    }

    public Gallery createGallery(LoginUserInfo loginUserInfo) {
        return Gallery.builder()
                .authorEmail(loginUserInfo.getEmail())
                .authorName(loginUserInfo.getName())
                .title(getTitle())
                .content(getContent())
                .build();
    }
}
