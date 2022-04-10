package admin.adminsiteserver.gallery.ui.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class UpdateGalleryRequest extends BaseGalleryRequest {
    private List<String> deleteFileUrls;

    public UpdateGalleryRequest(String title, String content, List<MultipartFile> files, List<String> deleteFileUrls) {
        super(title, content, files);
        this.deleteFileUrls = deleteFileUrls;
    }
}
