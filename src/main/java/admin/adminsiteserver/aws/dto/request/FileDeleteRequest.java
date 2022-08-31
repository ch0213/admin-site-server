package admin.adminsiteserver.aws.dto.request;

import admin.adminsiteserver.aws.dto.response.FilePath;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileDeleteRequest {
    @NotNull(message = "삭제할 파일의 URL을 첨부해주세요.")
    private List<FilePath> deleteFileUrls;
}
