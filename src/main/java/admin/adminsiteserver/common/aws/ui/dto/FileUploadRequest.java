package admin.adminsiteserver.common.aws.ui.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class FileUploadRequest {
    private MultipartFile image;
}
