package admin.adminsiteserver.announcement.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AnnouncementRepository {
    Announcement save(Announcement announcement);

    void delete(Announcement announcement);

    void flush();

    Optional<Announcement> findById(Long id);

    Optional<Announcement> findByIdAndDeletedIsFalse(Long id);

    Page<Announcement> findAllByDeletedIsFalse(Long announcementId, Pageable pageable);

    List<Announcement> findAllByDeletedIsFalse();
}
