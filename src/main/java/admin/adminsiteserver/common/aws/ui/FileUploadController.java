package admin.adminsiteserver.common.aws.ui;

import admin.adminsiteserver.common.aws.appliaction.FileUploadService;
import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.common.aws.ui.dto.FileUploadRequest;
import admin.adminsiteserver.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;
    private static final String IMAGE_UPLOAD_MESSAGE = "이미지 업로드 성공";

    @PostMapping
    public CommonResponse<FilePathDto> uploadImage(@RequestBody FileUploadRequest request) {
        FilePathDto filePathDto = fileUploadService.uploadImage(request);
        return CommonResponse.of(filePathDto, IMAGE_UPLOAD_MESSAGE);
    }
}
