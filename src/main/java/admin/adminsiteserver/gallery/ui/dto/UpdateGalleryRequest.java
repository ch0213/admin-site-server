package admin.adminsiteserver.gallery.ui.dto;

import admin.adminsiteserver.aws.infrastructure.dto.FilePathDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateGalleryRequest extends BaseGalleryRequest {
    private List<FilePathDto> deleteFileUrls;

    public UpdateGalleryRequest(String title, String content, List<FilePathDto> files, List<FilePathDto> deleteFileUrls) {
        super(title, content, files);
        this.deleteFileUrls = deleteFileUrls;
    }
}
