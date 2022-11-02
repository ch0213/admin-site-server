package admin.adminsiteserver.announcement.application;

import admin.adminsiteserver.announcement.exception.AnnouncementNotFoundException;
import admin.adminsiteserver.announcement.ui.response.AnnouncementResponse;
import admin.adminsiteserver.announcement.ui.response.AnnouncementSimpleResponse;
import admin.adminsiteserver.announcement.domain.Announcement;
import admin.adminsiteserver.announcement.domain.AnnouncementRepository;
import admin.adminsiteserver.common.response.PageInformation;
import admin.adminsiteserver.common.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnouncementQueryService {
    private final AnnouncementRepository announcementRepository;

    public AnnouncementResponse announcement(Long announcementId) {
        Announcement announcement = findById(announcementId);
        return AnnouncementResponse.from(announcement);
    }

    public PageResponse<List<AnnouncementSimpleResponse>> announcements(Long announcementId, Pageable pageable) {
        Pageable param = getSortedPageable(pageable);
        Page<AnnouncementSimpleResponse> announcements = findAll(announcementId, param);
        return PageResponse.of(announcements.getContent(), PageInformation.from(announcements));
    }

    private Announcement findById(Long announcementId) {
        return announcementRepository.findByIdAndDeletedIsFalse(announcementId)
                .orElseThrow(AnnouncementNotFoundException::new);
    }

    private Page<AnnouncementSimpleResponse> findAll(Long announcementId, Pageable pageable) {
        return announcementRepository.findAllByDeletedIsFalse(announcementId, pageable)
                .map(AnnouncementSimpleResponse::from);
    }

    private PageRequest getSortedPageable(Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());
    }
}
