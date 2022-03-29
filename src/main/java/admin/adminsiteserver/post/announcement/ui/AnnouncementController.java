package admin.adminsiteserver.post.announcement.ui;

import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.member.auth.util.LoginUser;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.post.announcement.application.AnnouncementService;
import admin.adminsiteserver.post.announcement.application.dto.UploadAnnouncementResponse;
import admin.adminsiteserver.post.announcement.ui.dto.UploadAnnouncementRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static admin.adminsiteserver.post.announcement.ui.AnnouncementResponseMessage.UPLOAD_SUCCESS;

@RestController
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping("/announcement")
    public CommonResponse<UploadAnnouncementResponse> uploadAnnouncement(UploadAnnouncementRequest request,
                                                   @LoginUser LoginUserInfo loginUserInfo) {
        UploadAnnouncementResponse response = announcementService.upload(request, loginUserInfo);
        return CommonResponse.of(response, UPLOAD_SUCCESS.getMessage());
    }
}
