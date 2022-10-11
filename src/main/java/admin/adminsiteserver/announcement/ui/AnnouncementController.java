package admin.adminsiteserver.announcement.ui;

import admin.adminsiteserver.announcement.application.AnnouncementQueryService;
import admin.adminsiteserver.announcement.ui.request.AnnouncementRequest;
import admin.adminsiteserver.announcement.ui.response.AnnouncementSimpleResponse;
import admin.adminsiteserver.announcement.ui.request.CommentRequest;
import admin.adminsiteserver.authentication.domain.LoginMember;
import admin.adminsiteserver.authentication.ui.AuthenticationPrincipal;
import admin.adminsiteserver.announcement.application.AnnouncementService;
import admin.adminsiteserver.announcement.ui.response.AnnouncementResponse;
import admin.adminsiteserver.common.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class AnnouncementController {
    private static final String COMMENT_URI_FORMAT = "/announcements/%d/comments/%d";

    private final AnnouncementQueryService announcementQueryService;
    private final AnnouncementService announcementService;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody AnnouncementRequest request,
                                       @AuthenticationPrincipal LoginMember loginMember) {
        Long announcementId = announcementService.upload(request, loginMember);
        return ResponseEntity.created(URI.create("/announcements/" + announcementId)).build();
    }

    @PutMapping("/{announcementId}")
    public ResponseEntity<Void> update(
            @PathVariable Long announcementId,
            @Valid @RequestBody AnnouncementRequest request,
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        announcementService.update(announcementId, request, loginMember);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{announcementId}")
    public ResponseEntity<Void> delete(@PathVariable Long announcementId, @AuthenticationPrincipal LoginMember loginMember) {
        announcementService.delete(announcementId, loginMember);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{announcementId}")
    public ResponseEntity<AnnouncementResponse> find(@PathVariable Long announcementId) {
        AnnouncementResponse response = announcementQueryService.getAnnouncement(announcementId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<List<AnnouncementSimpleResponse>>> findAll(Pageable pageable) {
        PageResponse<List<AnnouncementSimpleResponse>> response = announcementQueryService.getAnnouncements(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{announcementId}/comments")
    public ResponseEntity<Void> createComment(
            @PathVariable Long announcementId,
            @Valid @RequestBody CommentRequest request,
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        Long commentId = announcementService.addComment(announcementId, request, loginMember);
        return ResponseEntity.created(URI.create(String.format(COMMENT_URI_FORMAT, announcementId, commentId))).build();
    }

    @PutMapping("/{announcementId}/comments/{commentId}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long announcementId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest request,
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        announcementService.updateComment(announcementId, commentId, request, loginMember);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{announcementId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long announcementId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        announcementService.deleteComment(announcementId, commentId, loginMember);
        return ResponseEntity.noContent().build();
    }
}
