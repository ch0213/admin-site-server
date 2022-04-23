package admin.adminsiteserver.announcement.ui.dto;

import admin.adminsiteserver.announcement.domain.AnnouncementFilePath;
import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseAnnouncementRequest {
    private String title;
    private String content;
    private List<FilePathDto> files;

    public List<AnnouncementFilePath> toAnnouncementFilePaths() {
        return getFiles().stream()
                .map(filePathDto -> filePathDto.toFilePath(AnnouncementFilePath.class))
                .collect(Collectors.toList());
    }
}
