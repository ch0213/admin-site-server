package admin.adminsiteserver.qna.ui.dto;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateQnaRequest extends BaseQnaRequest {
    private List<FilePathDto> deleteFileUrls;

    public UpdateQnaRequest(String title, String content, List<FilePathDto> files, List<FilePathDto> deleteFileUrls) {
        super(title, content, files);
        this.deleteFileUrls = deleteFileUrls;
    }
}
