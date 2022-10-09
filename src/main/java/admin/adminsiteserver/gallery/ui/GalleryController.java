package admin.adminsiteserver.gallery.ui;

import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.gallery.application.GalleryService;
import admin.adminsiteserver.gallery.application.dto.GalleryResponse;
import admin.adminsiteserver.gallery.application.dto.GallerySimpleResponse;
import admin.adminsiteserver.gallery.ui.dto.GalleryCommentRequest;
import admin.adminsiteserver.gallery.ui.dto.UpdateGalleryRequest;
import admin.adminsiteserver.gallery.ui.dto.UploadGalleryRequest;
import admin.adminsiteserver.authentication.ui.AuthenticationPrincipal;
import admin.adminsiteserver.authentication.ui.LoginUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static admin.adminsiteserver.gallery.ui.GalleryResponseMessage.*;

@Slf4j
@RestController
@RequestMapping("/galleries")
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    @PostMapping
    public CommonResponse<GalleryResponse> uploadGallery(@Valid @RequestBody UploadGalleryRequest request,
                                                         @AuthenticationPrincipal LoginUserInfo loginUserInfo) {
        GalleryResponse response = galleryService.upload(request, loginUserInfo);
        return CommonResponse.of(response, GALLERY_UPLOAD_SUCCESS.getMessage());
    }

    @PutMapping("/{galleryId}")
    public CommonResponse<GalleryResponse> updateGallery(
            @Valid @RequestBody UpdateGalleryRequest request,
            @AuthenticationPrincipal LoginUserInfo loginUserInfo,
            @PathVariable Long galleryId
    )
    {
        GalleryResponse galleryResponse = galleryService.update(request, loginUserInfo, galleryId);
        return CommonResponse.of(galleryResponse, GALLERY_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{galleryId}")
    public CommonResponse<Void> deleteGallery(@AuthenticationPrincipal LoginUserInfo loginUserInfo, @PathVariable Long galleryId) {
        galleryService.delete(galleryId, loginUserInfo);
        return CommonResponse.from(GALLERY_DELETE_SUCCESS.getMessage());
    }

    @GetMapping("/{galleryId}")
    public CommonResponse<GalleryResponse> findGallery(@PathVariable Long galleryId) {
        return CommonResponse.of(galleryService.find(galleryId), GALLERY_FIND_SUCCESS.getMessage());
    }

    @GetMapping
    public CommonResponse<List<GallerySimpleResponse>> findAllGallery(Pageable pageable) {
        return galleryService.findAll(pageable);
    }

    @PostMapping("/{galleryId}/comments")
    public CommonResponse<Void> uploadComment(
            @PathVariable Long galleryId,
            @Valid @RequestBody GalleryCommentRequest request,
            @AuthenticationPrincipal LoginUserInfo loginUserInfo
    ) {
        galleryService.addComment(galleryId, request, loginUserInfo);
        return CommonResponse.from(COMMENT_UPLOAD_SUCCESS.getMessage());
    }

    @PutMapping("/{galleryId}/comments/{commentId}")
    public CommonResponse<Void> updateComment(
            @PathVariable Long galleryId,
            @PathVariable Long commentId,
            @Valid @RequestBody GalleryCommentRequest request,
            @AuthenticationPrincipal LoginUserInfo loginUserInfo
    ) {
        galleryService.updateComment(galleryId, commentId, request, loginUserInfo);
        return CommonResponse.from(COMMENT_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{galleryId}/comments/{commentId}")
    public CommonResponse<Void> deleteComment(
            @PathVariable Long galleryId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal LoginUserInfo loginUserInfo
    ) {
        galleryService.deleteComment(galleryId, commentId, loginUserInfo);
        return CommonResponse.from(COMMENT_DELETE_SUCCESS.getMessage());
    }
}
