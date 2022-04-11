package admin.adminsiteserver.qna.ui.dto;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerUpdateRequest {
    private String content;
    private List<FilePathDto> files;
    private List<FilePathDto> deleteFileUrls;
}
