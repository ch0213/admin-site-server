package admin.adminsiteserver.gallery.ui.dto;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.gallery.domain.Gallery;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UploadGalleryRequest extends BaseGalleryRequest {
    public UploadGalleryRequest(String title, String content, List<FilePathDto> files) {
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
