package admin.adminsiteserver.announcement.ui;

import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.member.auth.util.LoginUser;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.announcement.application.dto.AnnouncementDto;
import admin.adminsiteserver.announcement.application.AnnouncementService;
import admin.adminsiteserver.announcement.application.dto.AnnouncementResponse;
import admin.adminsiteserver.announcement.ui.dto.UpdateAnnouncementRequest;
import admin.adminsiteserver.announcement.ui.dto.UploadAnnouncementRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static admin.adminsiteserver.announcement.ui.AnnouncementResponseMessage.*;

@RestController
@RequestMapping("/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping
    public CommonResponse<AnnouncementResponse> uploadAnnouncement(UploadAnnouncementRequest request,
                                                                   @LoginUser LoginUserInfo loginUserInfo) {
        AnnouncementResponse response = announcementService.upload(request, loginUserInfo);
        return CommonResponse.of(response, ANNOUNCEMENT_UPLOAD_SUCCESS.getMessage());
    }

    @PutMapping("/{announcementId}")
    public CommonResponse<AnnouncementResponse> updateAnnouncement(
            UpdateAnnouncementRequest request,
            @LoginUser LoginUserInfo loginUserInfo,
            @PathVariable Long announcementId
    )
    {
        AnnouncementResponse announcementResponse = announcementService.update(request, loginUserInfo, announcementId);
        return CommonResponse.of(announcementResponse, ANNOUNCEMENT_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{announcementId}")
    public CommonResponse<Void> deleteAnnouncement(@LoginUser LoginUserInfo loginUserInfo, @PathVariable Long announcementId) {
        announcementService.delete(announcementId);
        return CommonResponse.from(ANNOUNCEMENT_DELETE_SUCCESS.getMessage());
    }

    @GetMapping("/{announcementId}")
    public CommonResponse<AnnouncementDto> findAnnouncement(@PathVariable Long announcementId) {
        return CommonResponse.of(announcementService.find(announcementId), ANNOUNCEMENT_FIND_SUCCESS.getMessage());
    }

    @GetMapping
    public CommonResponse<List<AnnouncementDto>> findAllAnnouncement(Pageable pageable) {
        return announcementService.findAll(pageable);
    }
}
