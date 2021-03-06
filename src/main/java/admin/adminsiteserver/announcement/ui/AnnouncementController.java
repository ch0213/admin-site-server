package admin.adminsiteserver.announcement.ui;

import admin.adminsiteserver.announcement.application.AnnouncementQueryService;
import admin.adminsiteserver.announcement.application.dto.AnnouncementSimpleResponse;
import admin.adminsiteserver.announcement.ui.dto.AnnouncementCommentRequest;
import admin.adminsiteserver.announcement.application.dto.AnnouncementCommentResponse;
import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.member.auth.util.LoginUser;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.announcement.application.AnnouncementService;
import admin.adminsiteserver.announcement.application.dto.AnnouncementResponse;
import admin.adminsiteserver.announcement.ui.dto.UpdateAnnouncementRequest;
import admin.adminsiteserver.announcement.ui.dto.UploadAnnouncementRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static admin.adminsiteserver.announcement.ui.AnnouncementResponseMessage.*;

@Slf4j
@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementQueryService announcementQueryService;
    private final AnnouncementService announcementService;

    @PostMapping
    public CommonResponse<AnnouncementResponse> uploadAnnouncement(@Valid @RequestBody UploadAnnouncementRequest request,
                                                                   @LoginUser LoginUserInfo loginUserInfo) {
        AnnouncementResponse response = announcementService.upload(request, loginUserInfo);
        return CommonResponse.of(response, ANNOUNCEMENT_UPLOAD_SUCCESS.getMessage());
    }

    @PutMapping("/{announcementId}")
    public CommonResponse<AnnouncementResponse> updateAnnouncement(
            @Valid @RequestBody UpdateAnnouncementRequest request,
            @LoginUser LoginUserInfo loginUserInfo,
            @PathVariable Long announcementId
    )
    {
        AnnouncementResponse announcementResponse = announcementService.update(request, loginUserInfo, announcementId);
        return CommonResponse.of(announcementResponse, ANNOUNCEMENT_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{announcementId}")
    public CommonResponse<Void> deleteAnnouncement(@LoginUser LoginUserInfo loginUserInfo, @PathVariable Long announcementId) {
        announcementService.delete(announcementId, loginUserInfo);
        return CommonResponse.from(ANNOUNCEMENT_DELETE_SUCCESS.getMessage());
    }

    @GetMapping("/{announcementId}")
    public CommonResponse<AnnouncementResponse> findAnnouncement(@PathVariable Long announcementId) {
        return CommonResponse.of(announcementQueryService.find(announcementId), ANNOUNCEMENT_FIND_SUCCESS.getMessage());
    }

    @GetMapping
    public CommonResponse<List<AnnouncementSimpleResponse>> findAllAnnouncement(Pageable pageable) {
        return announcementQueryService.findAll(pageable);
    }

    @PostMapping("/{announcementId}/comments")
    public CommonResponse<Void> uploadComment(
            @PathVariable Long announcementId,
            @Valid @RequestBody AnnouncementCommentRequest request,
            @LoginUser LoginUserInfo loginUserInfo
    ) {
        announcementService.addComment(announcementId, request, loginUserInfo);
        return CommonResponse.from(COMMENT_UPLOAD_SUCCESS.getMessage());
    }

    @PutMapping("/{announcementId}/comments/{commentId}")
    public CommonResponse<Void> updateComment(
            @PathVariable Long announcementId,
            @PathVariable Long commentId,
            @Valid @RequestBody AnnouncementCommentRequest request,
            @LoginUser LoginUserInfo loginUserInfo
    ) {
        announcementService.updateComment(announcementId, commentId, request, loginUserInfo);
        return CommonResponse.from(COMMENT_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{announcementId}/comments/{commentId}")
    public CommonResponse<Void> deleteComment(
            @PathVariable Long announcementId,
            @PathVariable Long commentId,
            @LoginUser LoginUserInfo loginUserInfo
    ) {
        announcementService.deleteComment(announcementId, commentId, loginUserInfo);
        return CommonResponse.from(COMMENT_DELETE_SUCCESS.getMessage());
    }
}
