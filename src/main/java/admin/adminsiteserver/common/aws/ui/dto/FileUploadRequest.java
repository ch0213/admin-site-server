package admin.adminsiteserver.common.aws.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
public class FileUploadRequest {

    @NotNull(message = "파일을 첨부해주세요.")
    private List<MultipartFile> files;
}
