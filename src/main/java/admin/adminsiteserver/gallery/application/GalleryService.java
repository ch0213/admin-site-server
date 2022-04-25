package admin.adminsiteserver.gallery.application;

import admin.adminsiteserver.common.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.common.dto.PageInfo;
import admin.adminsiteserver.gallery.application.dto.GalleryResponse;
import admin.adminsiteserver.gallery.application.dto.GallerySimpleResponse;
import admin.adminsiteserver.gallery.domain.Gallery;
import admin.adminsiteserver.gallery.domain.GalleryComment;
import admin.adminsiteserver.gallery.domain.GalleryRepository;
import admin.adminsiteserver.gallery.exception.NotExistGalleryException;
import admin.adminsiteserver.gallery.exception.UnauthorizedForGalleryCommentException;
import admin.adminsiteserver.gallery.exception.UnauthorizedForGalleryException;
import admin.adminsiteserver.gallery.ui.dto.GalleryCommentRequest;
import admin.adminsiteserver.gallery.ui.dto.UpdateGalleryRequest;
import admin.adminsiteserver.gallery.ui.dto.UploadGalleryRequest;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static admin.adminsiteserver.gallery.ui.GalleryResponseMessage.GALLERY_FIND_ALL_SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GalleryService {

    private final GalleryRepository galleryRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public GalleryResponse upload(UploadGalleryRequest request, LoginUserInfo loginUserInfo) {
        Gallery gallery = request.createGallery(loginUserInfo);
        gallery.saveFilePaths(request.toGalleryFilePaths());
        galleryRepository.save(gallery);
        return GalleryResponse.from(gallery);
    }

    @Transactional
    public GalleryResponse update(UpdateGalleryRequest request, LoginUserInfo loginUserInfo, Long id) {
        Gallery gallery = galleryRepository.findById(id)
                .orElseThrow(NotExistGalleryException::new);
        validateAuthorityForGallery(loginUserInfo, gallery);

        gallery.updateTitleAndContent(request.getTitle(), request.getContent());
        gallery.saveFilePaths(request.toGalleryFilePaths());
        gallery.deleteFilePaths(request.getDeleteFileUrls());
        s3Uploader.delete(request.getDeleteFileUrls());

        return GalleryResponse.from(gallery);
    }

    @Transactional
    public void delete(Long galleryId, LoginUserInfo loginUserInfo) {
        Gallery gallery = galleryRepository.findById(galleryId)
                .orElseThrow(NotExistGalleryException::new);
        validateAuthorityForGallery(loginUserInfo, gallery);
        s3Uploader.delete(gallery.findDeleteFilePaths());
        galleryRepository.delete(gallery);
    }

    @Transactional
    public void addComment(Long galleryId, GalleryCommentRequest request, LoginUserInfo loginUserInfo) {
        Gallery gallery = galleryRepository.findById(galleryId)
                .orElseThrow(NotExistGalleryException::new);
        gallery.addComment(request.toGalleryComment(loginUserInfo));
    }

    @Transactional
    public void updateComment(Long galleryId, Long commentId, GalleryCommentRequest request, LoginUserInfo loginUserInfo) {
        Gallery gallery = galleryRepository.findById(galleryId)
                .orElseThrow(NotExistGalleryException::new);
        GalleryComment comment = gallery.findUpdateOrDeleteComment(commentId);
        validateAuthorityForComment(loginUserInfo, comment);
        comment.updateComment(request.getComment());
    }

    @Transactional
    public void deleteComment(Long galleryId, Long commentId, LoginUserInfo loginUserInfo) {
        Gallery gallery = galleryRepository.findById(galleryId)
                .orElseThrow(NotExistGalleryException::new);
        GalleryComment comment = gallery.findUpdateOrDeleteComment(commentId);
        validateAuthorityForComment(loginUserInfo, comment);
        gallery.deleteComment(comment);
    }

    private void validateAuthorityForGallery(LoginUserInfo loginUserInfo, Gallery gallery) {
        if (loginUserInfo.isNotEqualUser(gallery.getAuthorEmail())) {
            throw new UnauthorizedForGalleryException();
        }
    }

    private void validateAuthorityForComment(LoginUserInfo loginUserInfo, GalleryComment comment) {
        if (loginUserInfo.isNotEqualUser(comment.getAuthorEmail())) {
            throw new UnauthorizedForGalleryCommentException();
        }
    }

    public GalleryResponse find(Long galleryId) {
        Gallery gallery = galleryRepository.findById(galleryId)
                .orElseThrow(NotExistGalleryException::new);
        return GalleryResponse.from(gallery);
    }

    public CommonResponse<List<GallerySimpleResponse>> findAll(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());
        Page<GallerySimpleResponse> gallerys = galleryRepository.findAll(pageRequest)
                .map(GallerySimpleResponse::from);

        return CommonResponse.of(gallerys.getContent(), PageInfo.from(gallerys), GALLERY_FIND_ALL_SUCCESS.getMessage());
    }
}
