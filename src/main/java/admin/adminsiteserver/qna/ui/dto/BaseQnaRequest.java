package admin.adminsiteserver.qna.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class BaseQnaRequest {
    private String title;
    private String content;
    private List<MultipartFile> images;
}
