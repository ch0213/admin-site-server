package admin.adminsiteserver.announcement.application;

import admin.adminsiteserver.announcement.application.dto.AnnouncementDto;
import admin.adminsiteserver.common.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.announcement.domain.AnnouncementFilePathRepository;
import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.common.dto.PageInfo;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.announcement.application.dto.AnnouncementResponse;
import admin.adminsiteserver.announcement.domain.Announcement;
import admin.adminsiteserver.announcement.domain.AnnouncementRepository;
import admin.adminsiteserver.announcement.domain.AnnouncementFilePath;
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
import java.util.stream.Collectors;

import static admin.adminsiteserver.announcement.ui.AnnouncementResponseMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementFilePathRepository filePathRepository;
    private final S3Uploader s3Uploader;
    private static final String ANNOUNCEMENT_IMAGE_PATH = "announcement/";

    @Transactional
    public AnnouncementResponse upload(UploadAnnouncementRequest request, LoginUserInfo loginUserInfo) {
        List<FilePathDto> filePathDtos = saveFiles(request);
        List<AnnouncementFilePath> imagePaths = getImagePathsFromDto(filePathDtos);
        Announcement announcement = createAnnouncement(request, loginUserInfo);
        announcement.addImages(imagePaths);
        announcementRepository.save(announcement);
        return AnnouncementResponse.of(announcement, filePathDtos);
    }

    @Transactional
    public AnnouncementResponse update(UpdateAnnouncementRequest request, LoginUserInfo loginUserInfo, Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(NotExistAnnouncementException::new);

        if (request.getImages() != null) {
            List<FilePathDto> filePathDtos = saveFiles(request);
            List<AnnouncementFilePath> filePaths = filePathRepository.saveAll(getImagePathsFromDto(filePathDtos));
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
                .map(AnnouncementFilePath::getFileUrl)
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

    private List<FilePathDto> saveFiles(BaseAnnouncementRequest request) {
        if (request.getImages() == null) {
            return null;
        }
        return s3Uploader.upload(request.getImages(), ANNOUNCEMENT_IMAGE_PATH);
    }

    private List<AnnouncementFilePath> getImagePathsFromDto(List<FilePathDto> imagePathDtos) {
        return imagePathDtos.stream()
                .map(filePathDto -> filePathDto.toFilePath(AnnouncementFilePath.class))
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
