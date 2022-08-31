package admin.adminsiteserver.announcement.ui.dto;

import admin.adminsiteserver.aws.dto.response.FilePath;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateAnnouncementRequest extends BaseAnnouncementRequest {
    private List<FilePath> deleteFileUrls;

    public UpdateAnnouncementRequest(String title, String content, List<FilePath> files, List<FilePath> deleteFileUrls) {
        super(title, content, files);
        this.deleteFileUrls = deleteFileUrls;
    }
}
