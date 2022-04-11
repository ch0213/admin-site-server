package admin.adminsiteserver.announcement.ui.dto;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseAnnouncementRequest {
    private String title;
    private String content;
    private List<FilePathDto> files;
}
