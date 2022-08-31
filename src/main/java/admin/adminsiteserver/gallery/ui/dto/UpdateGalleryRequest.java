package admin.adminsiteserver.gallery.ui.dto;

import admin.adminsiteserver.aws.dto.response.FilePath;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateGalleryRequest extends BaseGalleryRequest {
    private List<FilePath> deleteFileUrls;

    public UpdateGalleryRequest(String title, String content, List<FilePath> files, List<FilePath> deleteFileUrls) {
        super(title, content, files);
        this.deleteFileUrls = deleteFileUrls;
    }
}
