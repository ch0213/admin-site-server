package admin.adminsiteserver.announcement.infra;

import admin.adminsiteserver.announcement.domain.Announcement;
import admin.adminsiteserver.announcement.domain.AnnouncementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaAnnouncementRepository extends AnnouncementRepository, JpaRepository<Announcement, Long> {
    @Query("SELECT a FROM Announcement a WHERE a.deleted = false AND a.id <= ?1")
    Page<Announcement> findAllByDeletedIsFalse(Long announcementId, Pageable pageable);
}
