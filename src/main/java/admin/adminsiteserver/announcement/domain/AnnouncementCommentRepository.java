package admin.adminsiteserver.announcement.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementCommentRepository extends JpaRepository<AnnouncementComment, Long> {
}
