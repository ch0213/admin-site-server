package admin.adminsiteserver.announcement.ui.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class UpdateAnnouncementRequest extends BaseAnnouncementRequest{
    private List<String> deleteFileUrls;

    public UpdateAnnouncementRequest(String title, String content, List<MultipartFile> images, List<String> deleteFileUrls) {
        super(title, content, images);
        this.deleteFileUrls = deleteFileUrls;
    }
}
