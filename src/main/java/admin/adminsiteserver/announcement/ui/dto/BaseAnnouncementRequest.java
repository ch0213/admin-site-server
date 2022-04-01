package admin.adminsiteserver.announcement.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public abstract class BaseAnnouncementRequest {
    private String title;
    private String content;
    private List<MultipartFile> images;
}
