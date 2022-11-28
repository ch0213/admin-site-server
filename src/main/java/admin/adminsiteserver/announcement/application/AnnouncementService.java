package admin.adminsiteserver.announcement.application;

import admin.adminsiteserver.announcement.domain.AnnouncementComment;
import admin.adminsiteserver.announcement.domain.Author;
import admin.adminsiteserver.announcement.exception.AnnouncementNotFoundException;
import admin.adminsiteserver.announcement.ui.request.CommentRequest;
import admin.adminsiteserver.announcement.ui.request.AnnouncementRequest;
import admin.adminsiteserver.announcement.domain.Announcement;
import admin.adminsiteserver.announcement.domain.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    public Long upload(AnnouncementRequest request, Author author) {
        Announcement announcement = request.toEntity(author);
        announcementRepository.save(announcement);
        return announcement.getId();
    }

    public void update(Long announcementId, AnnouncementRequest request, Author author) {
        Announcement announcement = findById(announcementId);
        announcement.update(request.getTitle(), request.getContent(), request.getAnnouncementFiles(), author);
    }

    public void delete(Long announcementId, Author author) {
        Announcement announcement = findById(announcementId);
        announcement.delete(author);
    }

    public Long addComment(Long announcementId, CommentRequest request, Author author) {
        Announcement announcement = findById(announcementId);
        AnnouncementComment comment = announcement.addComment(request.getComment(), author);
        announcementRepository.flush();
        return comment.getId();
    }

    public void updateComment(Long announcementId, Long commentId, CommentRequest request, Author author) {
        Announcement announcement = findById(announcementId);
        announcement.updateComment(commentId, request.getComment(), author);
    }

    public void deleteComment(Long announcementId, Long commentId, Author author) {
        Announcement announcement = findById(announcementId);
        announcement.deleteComment(commentId, author);
    }

    private Announcement findById(Long id) {
        return announcementRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(AnnouncementNotFoundException::new);
    }
}
