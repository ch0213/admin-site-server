package admin.adminsiteserver.aws.ui;

import admin.adminsiteserver.aws.appliaction.FileUploadService;
import admin.adminsiteserver.aws.dto.response.FilePath;
import admin.adminsiteserver.aws.dto.request.FileDeleteRequest;
import admin.adminsiteserver.aws.dto.request.FileUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileUploadService fileUploadService;

    @PostMapping
    public ResponseEntity<List<FilePath>> uploadFile(@Valid FileUploadRequest request) {
        return ResponseEntity.ok(fileUploadService.uploadFile(request));
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteFile(@Valid @RequestBody FileDeleteRequest request) {
        fileUploadService.deleteFile(request);
        return ResponseEntity.noContent().build();
    }
}
