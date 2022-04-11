package admin.adminsiteserver.gallery.ui.dto;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseGalleryRequest {
    private String title;
    private String content;
    private List<FilePathDto> files;
}
