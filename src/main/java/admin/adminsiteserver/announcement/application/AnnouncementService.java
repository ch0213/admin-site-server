package admin.adminsiteserver.announcement.application;

import admin.adminsiteserver.announcement.domain.AnnouncementComment;
import admin.adminsiteserver.announcement.exception.UnauthorizedForAnnouncementCommentException;
import admin.adminsiteserver.announcement.exception.UnauthorizedForAnnouncementException;
import admin.adminsiteserver.announcement.ui.dto.AnnouncementCommentRequest;
import admin.adminsiteserver.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.authentication.ui.LoginUserInfo;
import admin.adminsiteserver.announcement.application.dto.AnnouncementResponse;
import admin.adminsiteserver.announcement.domain.Announcement;
import admin.adminsiteserver.announcement.domain.AnnouncementRepository;
import admin.adminsiteserver.announcement.exception.NotExistAnnouncementException;
import admin.adminsiteserver.announcement.ui.dto.UpdateAnnouncementRequest;
import admin.adminsiteserver.announcement.ui.dto.UploadAnnouncementRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final S3Uploader s3Uploader;

    public AnnouncementResponse upload(UploadAnnouncementRequest request, LoginUserInfo loginUserInfo) {
        Announcement announcement = request.createAnnouncement(loginUserInfo);
        announcement.saveFilePaths(request.toAnnouncementFilePaths());
        announcementRepository.save(announcement);
        return AnnouncementResponse.from(announcement);
    }

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

    public void delete(Long announcementId, LoginUserInfo loginUserInfo) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(NotExistAnnouncementException::new);
        validateAuthorityForAnnouncement(loginUserInfo, announcement);
        s3Uploader.delete(announcement.findDeleteFilePaths());
        announcementRepository.delete(announcement);
    }

    public void addComment(Long announcementId, AnnouncementCommentRequest request, LoginUserInfo loginUserInfo) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(NotExistAnnouncementException::new);
        announcement.addComment(request.toAnnouncementComment(loginUserInfo));
    }

    public void updateComment(Long announcementId, Long commentId, AnnouncementCommentRequest request, LoginUserInfo loginUserInfo) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(NotExistAnnouncementException::new);
        AnnouncementComment comment = announcement.findUpdateOrDeleteComment(commentId);
        validateAuthorityForComment(loginUserInfo, comment);
        comment.updateComment(request.getComment());
    }

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
        if (loginUserInfo.isNotEqualUser(loginUserInfo.getEmail())) {
            throw new UnauthorizedForAnnouncementCommentException();
        }
    }
}
