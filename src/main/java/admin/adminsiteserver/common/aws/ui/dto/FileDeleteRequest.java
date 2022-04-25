package admin.adminsiteserver.common.aws.ui.dto;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
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
    private List<FilePathDto> deleteFileUrls;
}
