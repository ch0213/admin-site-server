package admin.adminsiteserver.post.announcement.ui;

import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.member.auth.util.LoginUser;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.post.announcement.application.AnnouncementService;
import admin.adminsiteserver.post.announcement.application.dto.AnnouncementResponse;
import admin.adminsiteserver.post.announcement.ui.dto.UpdateAnnouncementRequest;
import admin.adminsiteserver.post.announcement.ui.dto.UploadAnnouncementRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static admin.adminsiteserver.post.announcement.ui.AnnouncementResponseMessage.*;

@RestController
@RequestMapping("/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping
    public CommonResponse<AnnouncementResponse> uploadAnnouncement(UploadAnnouncementRequest request,
                                                                   @LoginUser LoginUserInfo loginUserInfo) {
        AnnouncementResponse response = announcementService.upload(request, loginUserInfo);
        return CommonResponse.of(response, UPLOAD_SUCCESS.getMessage());
    }

    @PutMapping("/{announcementId}")
    public CommonResponse<Void> updateAnnouncement(
            UpdateAnnouncementRequest request,
            @LoginUser LoginUserInfo loginUserInfo,
            @PathVariable Long announcementId
    )
    {
        announcementService.update(request, loginUserInfo, announcementId);
        return CommonResponse.from(UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{announcementId}")
    public CommonResponse<Void> deleteAnnouncement(@LoginUser LoginUserInfo loginUserInfo, @PathVariable Long announcementId) {
        announcementService.delete(announcementId);
        return CommonResponse.from(DELETE_SUCCESS.getMessage());
    }
}
