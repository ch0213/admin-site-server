package admin.adminsiteserver.announcement.application;

import admin.adminsiteserver.announcement.application.dto.AnnouncementSimpleResponse;
import admin.adminsiteserver.announcement.domain.AnnouncementComment;
import admin.adminsiteserver.announcement.exception.UnauthorizedForAnnouncementCommentException;
import admin.adminsiteserver.announcement.exception.UnauthorizedForAnnouncementException;
import admin.adminsiteserver.announcement.ui.dto.AnnouncementCommentRequest;
import admin.adminsiteserver.common.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.common.dto.PageInfo;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.announcement.application.dto.AnnouncementResponse;
import admin.adminsiteserver.announcement.domain.Announcement;
import admin.adminsiteserver.announcement.domain.AnnouncementRepository;
import admin.adminsiteserver.announcement.exception.NotExistAnnouncementException;
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

import java.util.List;

import static admin.adminsiteserver.announcement.ui.AnnouncementResponseMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public AnnouncementResponse upload(UploadAnnouncementRequest request, LoginUserInfo loginUserInfo) {
        Announcement announcement = request.createAnnouncement(loginUserInfo);
        announcement.saveFilePaths(request.toAnnouncementFilePaths());
        announcementRepository.save(announcement);
        return AnnouncementResponse.from(announcement);
    }

    @Transactional
    public AnnouncementResponse update(UpdateAnnouncementRequest request, LoginUserInfo loginUserInfo, Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(NotExistAnnouncementException::new);
        validateAuthorityForAnnouncement(loginUserInfo, announcement);

        announcement.updateTitleAndContent(request.getTitle(), request.getContent());
        announcement.saveFilePaths(request.toAnnouncementFilePaths());
        announcement.deleteFilePaths(request.getDeleteFileUrls());
        s3Uploader.delete(request.getDeleteFileUrls());

        return AnnouncementResponse.from(announcement);
    }

    @Transactional
    public void delete(Long announcementId, LoginUserInfo loginUserInfo) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(NotExistAnnouncementException::new);
        validateAuthorityForAnnouncement(loginUserInfo, announcement);
        s3Uploader.delete(announcement.findDeleteFilePaths());
        announcementRepository.delete(announcement);
    }

    @Transactional
    public void addComment(Long announcementId, AnnouncementCommentRequest request, LoginUserInfo loginUserInfo) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(NotExistAnnouncementException::new);
        announcement.addComment(request.toAnnouncementComment(loginUserInfo));
    }

    @Transactional
    public void updateComment(Long announcementId, Long commentId, AnnouncementCommentRequest request, LoginUserInfo loginUserInfo) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(NotExistAnnouncementException::new);
        AnnouncementComment comment = announcement.findUpdateOrDeleteComment(commentId);
        validateAuthorityForComment(loginUserInfo, comment);
        comment.updateComment(request.getComment());
    }

    @Transactional
    public void deleteComment(Long announcementId, Long commentId, LoginUserInfo loginUserInfo) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(NotExistAnnouncementException::new);
        AnnouncementComment comment = announcement.findUpdateOrDeleteComment(commentId);
        validateAuthorityForComment(loginUserInfo, comment);
        announcement.deleteComment(comment);
    }

    private void validateAuthorityForAnnouncement(LoginUserInfo loginUserInfo, Announcement announcement) {
        if (loginUserInfo.isNotEqualUser(announcement.getAuthorEmail())) {
            throw new UnauthorizedForAnnouncementException();
        }
    }

    private void validateAuthorityForComment(LoginUserInfo loginUserInfo, AnnouncementComment comment) {
        if (loginUserInfo.isNotEqualUser(comment.getAuthorEmail())) {
            throw new UnauthorizedForAnnouncementCommentException();
        }
    }

    public AnnouncementResponse find(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(NotExistAnnouncementException::new);
        return AnnouncementResponse.from(announcement);
    }

    public CommonResponse<List<AnnouncementSimpleResponse>> findAll(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());
        Page<AnnouncementSimpleResponse> announcements = announcementRepository.findAll(pageRequest)
                .map(AnnouncementSimpleResponse::from);

        return CommonResponse.of(announcements.getContent(), PageInfo.from(announcements), ANNOUNCEMENT_FIND_ALL_SUCCESS.getMessage());
    }
}
