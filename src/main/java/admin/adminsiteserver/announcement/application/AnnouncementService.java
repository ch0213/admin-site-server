package admin.adminsiteserver.announcement.application;

import admin.adminsiteserver.announcement.exception.UnauthorizedForAnnouncementException;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private static final String ANNOUNCEMENT_FILE_PATH = "announcement/";

    @Transactional
    public AnnouncementResponse upload(UploadAnnouncementRequest request, LoginUserInfo loginUserInfo) {
        Announcement announcement = request.createAnnouncement(loginUserInfo);
        List<FilePathDto> filePathDtos = new ArrayList<>();
        if (request.getFiles() != null) {
            filePathDtos = saveFiles(request);
            List<AnnouncementFilePath> filePaths = getFilePathsFromDto(filePathDtos);
            announcement.addFiles(filePaths);
        }
        announcementRepository.save(announcement);
        return AnnouncementResponse.of(announcement, filePathDtos);
    }

    @Transactional
    public AnnouncementResponse update(UpdateAnnouncementRequest request, LoginUserInfo loginUserInfo, Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(NotExistAnnouncementException::new);
        validateAuthorityForAnnouncement(loginUserInfo, announcement);
        announcement.updateTitleAndContent(request.getTitle(), request.getContent());

        if (request.getFiles() != null) {
            List<FilePathDto> filePathDtos = saveFiles(request);
            List<AnnouncementFilePath> filePaths = filePathRepository.saveAll(getFilePathsFromDto(filePathDtos));
            announcement.addFiles(filePaths);
        }

        if (request.getDeleteFileUrls() != null) {
            announcement.deleteFiles(request.getDeleteFileUrls());
            s3Uploader.delete(request.getDeleteFileUrls());
        }

        return AnnouncementResponse.of(announcement, getFilePathDtosFromAnnouncement(announcement));
    }

    @Transactional
    public void delete(Long announcementId, LoginUserInfo loginUserInfo) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(NotExistAnnouncementException::new);
        validateAuthorityForAnnouncement(loginUserInfo, announcement);
        List<String> deleteFileURls = announcement.getFiles().stream()
                .map(AnnouncementFilePath::getFileUrl)
                .collect(Collectors.toList());
        s3Uploader.delete(deleteFileURls);

        announcementRepository.delete(announcement);
    }

    private void validateAuthorityForAnnouncement(LoginUserInfo loginUserInfo, Announcement announcement) {
        if (!loginUserInfo.getUserId().equals(announcement.getAuthorId())) {
            throw new UnauthorizedForAnnouncementException();
        }
    }

    public AnnouncementResponse find(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(NotExistAnnouncementException::new);
        return AnnouncementResponse.from(announcement);
    }

    public CommonResponse<List<AnnouncementResponse>> findAll(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());
        Page<AnnouncementResponse> announcements = announcementRepository.findAll(pageRequest)
                .map(AnnouncementResponse::from);

        return CommonResponse.of(announcements.getContent(), PageInfo.from(announcements), ANNOUNCEMENT_FIND_ALL_SUCCESS.getMessage());
    }

    private List<FilePathDto> getFilePathDtosFromAnnouncement(Announcement announcement) {
        return announcement.getFiles().stream()
                .map(FilePathDto::from)
                .collect(Collectors.toList());
    }

    private List<FilePathDto> saveFiles(BaseAnnouncementRequest request) {
        if (request.getFiles() == null) {
            return null;
        }
        return s3Uploader.upload(request.getFiles(), ANNOUNCEMENT_FILE_PATH);
    }

    private List<AnnouncementFilePath> getFilePathsFromDto(List<FilePathDto> filePathDtos) {
        return filePathDtos.stream()
                .map(filePathDto -> filePathDto.toFilePath(AnnouncementFilePath.class))
                .collect(Collectors.toList());
    }
}
