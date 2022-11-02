package admin.adminsiteserver.announcement.application;

import admin.adminsiteserver.announcement.domain.AnnouncementComment;
import admin.adminsiteserver.announcement.exception.AnnouncementNotFoundException;
import admin.adminsiteserver.announcement.ui.request.CommentRequest;
import admin.adminsiteserver.announcement.ui.request.AnnouncementRequest;
import admin.adminsiteserver.authentication.domain.LoginMember;
import admin.adminsiteserver.announcement.domain.Announcement;
import admin.adminsiteserver.announcement.domain.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static admin.adminsiteserver.announcement.util.LoginMemberConverter.author;

@Service
@Transactional
@RequiredArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    public Long upload(AnnouncementRequest request, LoginMember loginMember) {
        Announcement announcement = request.toEntity(loginMember);
        announcementRepository.save(announcement);
        return announcement.getId();
    }

    public void update(Long announcementId, AnnouncementRequest request, LoginMember loginMember) {
        Announcement announcement = findById(announcementId);
        announcement.update(request.getTitle(), request.getContent(), request.getAnnouncementFiles(), author(loginMember));
    }

    public void delete(Long announcementId, LoginMember loginMember) {
        Announcement announcement = findById(announcementId);
        announcement.delete(author(loginMember));
    }

    public Long addComment(Long announcementId, CommentRequest request, LoginMember loginMember) {
        Announcement announcement = findById(announcementId);
        AnnouncementComment comment = announcement.addComment(request.getComment(), author(loginMember));
        announcementRepository.flush();
        return comment.getId();
    }

    public void updateComment(Long announcementId, Long commentId, CommentRequest request, LoginMember loginMember) {
        Announcement announcement = findById(announcementId);
        announcement.updateComment(commentId, request.getComment(), author(loginMember));
    }

    public void deleteComment(Long announcementId, Long commentId, LoginMember loginMember) {
        Announcement announcement = findById(announcementId);
        announcement.deleteComment(commentId, author(loginMember));
    }

    private Announcement findById(Long id) {
        return announcementRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(AnnouncementNotFoundException::new);
    }
}
