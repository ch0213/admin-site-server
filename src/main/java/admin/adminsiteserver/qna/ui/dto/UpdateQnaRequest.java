package admin.adminsiteserver.qna.ui.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class UpdateQnaRequest extends BaseQnaRequest{
    private List<String> deleteFileUrls;

    public UpdateQnaRequest(String title, String content, List<MultipartFile> images, List<String> deleteFileUrls) {
        super(title, content, images);
        this.deleteFileUrls = deleteFileUrls;
    }
}
