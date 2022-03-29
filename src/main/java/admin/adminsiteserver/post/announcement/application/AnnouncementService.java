package admin.adminsiteserver.post.announcement.application;

import admin.adminsiteserver.common.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.post.announcement.application.dto.UploadAnnouncementResponse;
import admin.adminsiteserver.post.announcement.domain.Announcement;
import admin.adminsiteserver.post.announcement.domain.AnnouncementRepository;
import admin.adminsiteserver.post.announcement.domain.FilePath;
import admin.adminsiteserver.post.announcement.ui.dto.UploadAnnouncementRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final S3Uploader s3Uploader;
    private static final String ANNOUNCEMENT_IMAGE_DIR = "announcement/";

    public UploadAnnouncementResponse upload (
            UploadAnnouncementRequest request,
            LoginUserInfo loginUserInfo
    ) {
        List<FilePathDto> imagePathDtos = s3Uploader.upload(request.getImages(), ANNOUNCEMENT_IMAGE_DIR);
        List<FilePath> imagePaths = imagePathDtos.stream()
                .map(FilePathDto::toFilePath)
                .collect(Collectors.toList());

        Announcement announcement = createAnnouncement(loginUserInfo, request);
        announcement.saveImages(imagePaths);
        announcementRepository.save(announcement);

        return UploadAnnouncementResponse.of(announcement, imagePathDtos);
    }

    private Announcement createAnnouncement(
            LoginUserInfo loginUserInfo,
            UploadAnnouncementRequest request
    ) {
        return Announcement.builder()
                .authorId(loginUserInfo.getUserId())
                .authorName(loginUserInfo.getName())
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }
}
