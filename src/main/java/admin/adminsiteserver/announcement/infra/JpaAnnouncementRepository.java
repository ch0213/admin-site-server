package admin.adminsiteserver.announcement.infra;

import admin.adminsiteserver.announcement.domain.Announcement;
import admin.adminsiteserver.announcement.domain.AnnouncementRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAnnouncementRepository extends AnnouncementRepository, JpaRepository<Announcement, Long> {
}
