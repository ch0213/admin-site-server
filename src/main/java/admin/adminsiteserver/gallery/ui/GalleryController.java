package admin.adminsiteserver.gallery.ui;

import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.gallery.application.GalleryService;
import admin.adminsiteserver.gallery.application.dto.GalleryResponse;
import admin.adminsiteserver.gallery.ui.dto.GalleryCommentRequest;
import admin.adminsiteserver.gallery.ui.dto.UpdateGalleryRequest;
import admin.adminsiteserver.gallery.ui.dto.UploadGalleryRequest;
import admin.adminsiteserver.member.auth.util.LoginUser;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static admin.adminsiteserver.gallery.ui.GalleryResponseMessage.*;

@Slf4j
@RestController
@RequestMapping("/gallery")
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    @PostMapping
    public CommonResponse<GalleryResponse> uploadGallery(@RequestBody UploadGalleryRequest request,
                                                         @LoginUser LoginUserInfo loginUserInfo) {
        GalleryResponse response = galleryService.upload(request, loginUserInfo);
        return CommonResponse.of(response, GALLERY_UPLOAD_SUCCESS.getMessage());
    }

    @PutMapping("/{galleryId}")
    public CommonResponse<GalleryResponse> updateGallery(
            @RequestBody UpdateGalleryRequest request,
            @LoginUser LoginUserInfo loginUserInfo,
            @PathVariable Long galleryId
    )
    {
        GalleryResponse galleryResponse = galleryService.update(request, loginUserInfo, galleryId);
        return CommonResponse.of(galleryResponse, GALLERY_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{galleryId}")
    public CommonResponse<Void> deleteGallery(@LoginUser LoginUserInfo loginUserInfo, @PathVariable Long galleryId) {
        galleryService.delete(galleryId, loginUserInfo);
        return CommonResponse.from(GALLERY_DELETE_SUCCESS.getMessage());
    }

    @GetMapping("/{galleryId}")
    public CommonResponse<GalleryResponse> findGallery(@PathVariable Long galleryId) {
        return CommonResponse.of(galleryService.find(galleryId), GALLERY_FIND_SUCCESS.getMessage());
    }

    @GetMapping
    public CommonResponse<List<GalleryResponse>> findAllGallery(Pageable pageable) {
        return galleryService.findAll(pageable);
    }

    @PostMapping("/{galleryId}/comment")
    public CommonResponse<Void> uploadComment(
            @PathVariable Long galleryId,
            @RequestBody GalleryCommentRequest request,
            @LoginUser LoginUserInfo loginUserInfo
    ) {
        galleryService.addComment(galleryId, request, loginUserInfo);
        return CommonResponse.from(COMMENT_UPLOAD_SUCCESS.getMessage());
    }

    @PutMapping("/{galleryId}/comment/{commentId}")
    public CommonResponse<Void> updateComment(
            @PathVariable Long galleryId,
            @PathVariable Long commentId,
            @RequestBody GalleryCommentRequest request,
            @LoginUser LoginUserInfo loginUserInfo
    ) {
        galleryService.updateComment(galleryId, commentId, request, loginUserInfo);
        return CommonResponse.from(COMMENT_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{galleryId}/comment/{commentId}")
    public CommonResponse<Void> deleteComment(
            @PathVariable Long galleryId,
            @PathVariable Long commentId,
            @LoginUser LoginUserInfo loginUserInfo
    ) {
        galleryService.deleteComment(galleryId, commentId, loginUserInfo);
        return CommonResponse.from(COMMENT_DELETE_SUCCESS.getMessage());
    }
}
