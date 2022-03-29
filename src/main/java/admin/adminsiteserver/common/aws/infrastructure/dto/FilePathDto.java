package admin.adminsiteserver.common.aws.infrastructure.dto;

import admin.adminsiteserver.common.domain.FilePath;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FilePathDto {
    private String fileName;
    private String fileUrl;

    public static FilePathDto of(String fileName, String fileUrl) {
        return new FilePathDto(fileName, fileUrl);
    }

    public FilePath toFilePath() {
        return new FilePath(fileName, fileUrl);
    }
}
