package admin.adminsiteserver.aws.appliaction;

import admin.adminsiteserver.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.aws.ui.dto.FileDeleteRequest;
import admin.adminsiteserver.aws.ui.dto.FileUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final S3Uploader s3Uploader;
    private static final String FILE_STORAGE_PATH = "file/";

    public List<FilePathDto> uploadFile(FileUploadRequest request) {
        return s3Uploader.upload(request.getFiles(), FILE_STORAGE_PATH);
    }

    public void deleteFile(FileDeleteRequest request) {
        s3Uploader.delete(request.getDeleteFileUrls());
    }
}
