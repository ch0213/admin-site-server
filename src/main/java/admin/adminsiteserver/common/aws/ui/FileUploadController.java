package admin.adminsiteserver.common.aws.ui;

import admin.adminsiteserver.common.aws.appliaction.FileUploadService;
import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.common.aws.ui.dto.FileUploadRequest;
import admin.adminsiteserver.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;
    private static final String FILE_UPLOAD_MESSAGE = "파일 업로드 성공";

    @PostMapping
    public CommonResponse<FilePathDto> uploadFile(FileUploadRequest request) {
        FilePathDto filePathDto = fileUploadService.uploadImage(request);
        return CommonResponse.of(filePathDto, FILE_UPLOAD_MESSAGE);
    }
}
