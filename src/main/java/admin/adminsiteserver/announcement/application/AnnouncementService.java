package admin.adminsiteserver.announcement.application;

import admin.adminsiteserver.common.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.common.domain.FilePathRepository;
import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.common.dto.FilePathDto;
import admin.adminsiteserver.common.dto.PageInfo;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.announcement.application.dto.AnnouncementResponse;
import admin.adminsiteserver.announcement.domain.Announcement;
import admin.adminsiteserver.announcement.domain.AnnouncementRepository;
import admin.adminsiteserver.common.domain.FilePath;
import admin.adminsiteserver.announcement.exception.NotExistAnnouncementException;
import admin.adminsiteserver.announcement.ui.dto.BaseAnnouncementRequest;
import admin.adminsiteserver.announcement.ui.dto.UpdateAnnouncementRequest;
import admin.adminsiteserver.announcement.ui.dto.UploadAnnouncementRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static admin.adminsiteserver.announcement.ui.AnnouncementResponseMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final FilePathRepository filePathRepository;
    private final S3Uploader s3Uploader;
    private static final String ANNOUNCEMENT_IMAGE_PATH = "announcement/";

    @Transactional
    public AnnouncementResponse upload(UploadAnnouncementRequest request, LoginUserInfo loginUserInfo) {
        List<FilePath> imagePaths = saveFilesAndGetImagePaths(request);
        Announcement announcement = createAnnouncement(request, loginUserInfo);
        announcement.addImages(imagePaths);
        announcementRepository.save(announcement);
        return AnnouncementResponse.of(announcement, getImagePathDtosFromAnnouncement(announcement));
    }

    @Transactional
    public AnnouncementResponse update(UpdateAnnouncementRequest request, LoginUserInfo loginUserInfo, Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(NotExistAnnouncementException::new);

        if (request.getImages() != null) {
            List<FilePath> imagePaths = saveFilesAndGetImagePaths(request);
            List<FilePath> filePaths = filePathRepository.saveAll(Objects.requireNonNull(imagePaths));
            announcement.addImages(filePaths);
        }

        if (request.getDeleteFileUrls() != null) {
            announcement.deleteImages(request.getDeleteFileUrls());
            s3Uploader.delete(request.getDeleteFileUrls());
        }

        return AnnouncementResponse.of(announcement, getImagePathDtosFromAnnouncement(announcement));
    }

    @Transactional
    public void delete(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(NotExistAnnouncementException::new);

        List<String> deleteFileURls = announcement.getImages().stream()
                .map(FilePath::getFileUrl)
                .collect(Collectors.toList());
        s3Uploader.delete(deleteFileURls);

        announcementRepository.delete(announcement);
    }

    public AnnouncementDto find(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(NotExistAnnouncementException::new);
        return AnnouncementDto.from(announcement);
    }

    public CommonResponse<List<AnnouncementDto>> findAll(Pageable pageable) {
        Page<AnnouncementDto> announcements = announcementRepository.findAll(pageable)
                .map(AnnouncementDto::from);

        return CommonResponse.of(announcements.getContent(), PageInfo.from(announcements), ANNOUNCEMENT_FIND_ALL_SUCCESS.getMessage());
    }

    private List<FilePathDto> getImagePathDtosFromAnnouncement(Announcement announcement) {
        return announcement.getImages().stream()
                .map(FilePathDto::from)
                .collect(Collectors.toList());
    }

    private List<FilePath> saveFilesAndGetImagePaths(BaseAnnouncementRequest request) {
        if (request.getImages() == null) {
            return null;
        }
        List<FilePathDto> imagePathDtos = s3Uploader.upload(request.getImages(), ANNOUNCEMENT_IMAGE_PATH);
        return imagePathDtos.stream()
                .map(FilePathDto::toFilePath)
                .collect(Collectors.toList());
    }

    private Announcement createAnnouncement(UploadAnnouncementRequest request, LoginUserInfo loginUserInfo) {
        return Announcement.builder()
                .authorId(loginUserInfo.getUserId())
                .authorName(loginUserInfo.getName())
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }
}