package admin.adminsiteserver.common.aws.appliaction;

import admin.adminsiteserver.common.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.common.aws.ui.dto.FileUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final S3Uploader s3Uploader;
    private static final String FILE_STORAGE_PATH = "file/";

    public FilePathDto uploadImage(FileUploadRequest request) {
        return s3Uploader.upload(request.getImage(), FILE_STORAGE_PATH);
    }
}
