package admin.adminsiteserver.announcement.infra;

import admin.adminsiteserver.announcement.domain.Announcement;
import admin.adminsiteserver.announcement.domain.AnnouncementRepository;
import admin.adminsiteserver.common.event.MemberUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AnnouncementEventListener {
    private final AnnouncementRepository announcementRepository;

    @Async
    @EventListener
    public void handle(MemberUpdateEvent event) {
        List<Announcement> announcements = announcementRepository.findAllByDeletedIsFalse();
        announcements.forEach(announcement -> announcement.updateAuthor(event.getAuthor()));
    }
}
