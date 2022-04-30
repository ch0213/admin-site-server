package admin.adminsiteserver.announcement.application;

import admin.adminsiteserver.announcement.application.dto.AnnouncementResponse;
import admin.adminsiteserver.announcement.application.dto.AnnouncementSimpleResponse;
import admin.adminsiteserver.announcement.domain.Announcement;
import admin.adminsiteserver.announcement.domain.AnnouncementRepository;
import admin.adminsiteserver.announcement.exception.NotExistAnnouncementException;
import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.common.dto.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static admin.adminsiteserver.announcement.ui.AnnouncementResponseMessage.ANNOUNCEMENT_FIND_ALL_SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnouncementQueryService {

    private final AnnouncementRepository announcementRepository;

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
