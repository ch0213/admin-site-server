package admin.adminsiteserver.common.aws.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class FileUploadRequest {
    private MultipartFile image;
}
