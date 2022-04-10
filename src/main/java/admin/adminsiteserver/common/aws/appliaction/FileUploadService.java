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
    private static final String IMAGE_PATH = "image/";

    public FilePathDto uploadImage(FileUploadRequest request) {
        return s3Uploader.upload(request.getImage(), IMAGE_PATH);
    }
}
