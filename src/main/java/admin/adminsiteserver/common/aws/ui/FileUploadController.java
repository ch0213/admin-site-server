package admin.adminsiteserver.common.aws.ui;

import admin.adminsiteserver.common.aws.appliaction.FileUploadService;
import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.common.aws.ui.dto.FileDeleteRequest;
import admin.adminsiteserver.common.aws.ui.dto.FileUploadRequest;
import admin.adminsiteserver.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;
    private static final String FILE_UPLOAD_MESSAGE = "파일 업로드 성공";
    private static final String FILE_DELETE_MESSAGE = "파일 삭제 성공";

    @PostMapping
    public CommonResponse<List<FilePathDto>> uploadFile(FileUploadRequest request) {
        List<FilePathDto> filePathDto = fileUploadService.uploadFile(request);
        return CommonResponse.of(filePathDto, FILE_UPLOAD_MESSAGE);
    }

    @PostMapping("/delete")
    public CommonResponse<Void> deleteFile(@RequestBody FileDeleteRequest request) {
        fileUploadService.deleteFile(request);
        return CommonResponse.from(FILE_DELETE_MESSAGE);
    }
}
