package admin.adminsiteserver.aws.appliaction;

import admin.adminsiteserver.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.aws.dto.response.FilePath;
import admin.adminsiteserver.aws.dto.request.FileDeleteRequest;
import admin.adminsiteserver.aws.dto.request.FileUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    private static final String FILE_STORAGE_PATH = "file/";

    private final S3Uploader s3Uploader;

    public List<FilePath> uploadFile(FileUploadRequest request) {
        return s3Uploader.upload(request.getFiles(), FILE_STORAGE_PATH);
    }

    public void deleteFile(FileDeleteRequest request) {
        s3Uploader.delete(request.getDeleteFileUrls());
    }
}
