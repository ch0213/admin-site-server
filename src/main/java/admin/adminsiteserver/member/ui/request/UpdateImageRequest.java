package admin.adminsiteserver.member.ui.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class UpdateImageRequest {
    @NotNull(message = "이미지를 첨부해주세요.")
    private MultipartFile image;
}
