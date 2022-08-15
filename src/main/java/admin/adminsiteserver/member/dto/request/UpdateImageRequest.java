package admin.adminsiteserver.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UpdateImageRequest {
    @NotNull(message = "이미지를 첨부해주세요.")
    private MultipartFile image;
}
