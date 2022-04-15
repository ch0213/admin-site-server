package admin.adminsiteserver.common.aws.ui.dto;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileDeleteRequest {
    private List<FilePathDto> deleteFileUrls;
}
