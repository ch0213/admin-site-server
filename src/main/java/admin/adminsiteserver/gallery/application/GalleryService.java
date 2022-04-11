package admin.adminsiteserver.gallery.application;

import admin.adminsiteserver.common.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.common.dto.PageInfo;
import admin.adminsiteserver.gallery.application.dto.GalleryResponse;
import admin.adminsiteserver.gallery.domain.Gallery;
import admin.adminsiteserver.gallery.domain.GalleryFilePath;
import admin.adminsiteserver.gallery.domain.GalleryRepository;
import admin.adminsiteserver.gallery.exception.NotExistGalleryException;
import admin.adminsiteserver.gallery.exception.UnauthorizedForGalleryException;
import admin.adminsiteserver.gallery.ui.GalleryResponseMessage;
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
import java.util.stream.Collectors;

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
        galleryRepository.save(gallery);
        return GalleryResponse.from(gallery);
    }

    @Transactional
    public GalleryResponse update(UpdateGalleryRequest request, LoginUserInfo loginUserInfo, Long id) {
        Gallery gallery = galleryRepository.findById(id)
                .orElseThrow(NotExistGalleryException::new);
        validateAuthorityForGallery(loginUserInfo, gallery);
        gallery.updateTitleAndContent(request.getTitle(), request.getContent());

        gallery.getFiles().addAll(request.getFiles().stream()
                .map(filePathDto -> filePathDto.toFilePath(GalleryFilePath.class))
                .collect(Collectors.toList()));

       gallery.deleteFiles(request.getDeleteFileUrls());
       s3Uploader.delete(request.getDeleteFileUrls());

        return GalleryResponse.from(gallery);
    }

    @Transactional
    public void delete(Long galleryId, LoginUserInfo loginUserInfo) {
        Gallery gallery = galleryRepository.findById(galleryId)
                .orElseThrow(NotExistGalleryException::new);
        validateAuthorityForGallery(loginUserInfo, gallery);
        List<FilePathDto> deleteFileURls = gallery.getFiles().stream()
                .map(FilePathDto::from)
                .collect(Collectors.toList());
        s3Uploader.delete(deleteFileURls);
        galleryRepository.delete(gallery);
    }

    private void validateAuthorityForGallery(LoginUserInfo loginUserInfo, Gallery gallery) {
        if (!loginUserInfo.getUserId().equals(gallery.getAuthorId())) {
            throw new UnauthorizedForGalleryException();
        }
    }

    public GalleryResponse find(Long galleryId) {
        Gallery gallery = galleryRepository.findById(galleryId)
                .orElseThrow(NotExistGalleryException::new);
        return GalleryResponse.from(gallery);
    }

    public CommonResponse<List<GalleryResponse>> findAll(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());
        Page<GalleryResponse> gallerys = galleryRepository.findAll(pageRequest)
                .map(GalleryResponse::from);

        return CommonResponse.of(gallerys.getContent(), PageInfo.from(gallerys), GALLERY_FIND_ALL_SUCCESS.getMessage());
    }
}
