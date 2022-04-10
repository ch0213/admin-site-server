package admin.adminsiteserver.gallery.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public abstract class BaseGalleryRequest {
    private String title;
    private String content;
    private List<MultipartFile> files;
}
