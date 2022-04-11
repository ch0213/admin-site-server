package admin.adminsiteserver.gallery.ui.dto;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.gallery.domain.Gallery;
import admin.adminsiteserver.gallery.domain.GalleryFilePath;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class UploadGalleryRequest extends BaseGalleryRequest {
    public UploadGalleryRequest(String title, String content, List<FilePathDto> files) {
        super(title, content, files);
    }

    public Gallery createGallery(LoginUserInfo loginUserInfo) {
        List<GalleryFilePath> filePaths = new ArrayList<>();
        if (getFiles() != null) {
            filePaths = getFiles().stream()
                    .map(filePathDto -> filePathDto.toFilePath(GalleryFilePath.class))
                    .collect(Collectors.toList());
        }

        return Gallery.builder()
                .authorId(loginUserInfo.getUserId())
                .authorName(loginUserInfo.getName())
                .title(getTitle())
                .content(getContent())
                .files(filePaths)
                .build();
    }
}
