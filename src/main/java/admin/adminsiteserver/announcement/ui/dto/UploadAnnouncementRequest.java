package admin.adminsiteserver.announcement.ui.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class UploadAnnouncementRequest extends BaseAnnouncementRequest{
    public UploadAnnouncementRequest(String title, String content, List<MultipartFile> images) {
        super(title, content, images);
    }
}
