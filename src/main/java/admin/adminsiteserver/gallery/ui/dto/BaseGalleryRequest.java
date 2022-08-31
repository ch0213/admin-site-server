package admin.adminsiteserver.gallery.ui.dto;

import admin.adminsiteserver.aws.dto.response.FilePath;
import admin.adminsiteserver.gallery.domain.GalleryFilePath;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseGalleryRequest {
    @NotBlank(message = "제목은 빈칸일 수 없습니다.")
    private String title;

    @NotBlank(message = "내용은 빈칸일 수 없습니다.")
    private String content;
    private List<FilePath> files;

    public List<GalleryFilePath> toGalleryFilePaths() {
        if (files == null) return new ArrayList<>();
        return files.stream()
                .map(filePathDto -> filePathDto.toFilePath(GalleryFilePath.class))
                .collect(Collectors.toList());
    }
}
