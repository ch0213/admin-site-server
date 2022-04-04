package admin.adminsiteserver.qna.application;

import admin.adminsiteserver.common.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.member.auth.util.LoginUser;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.qna.application.dto.QnaResponse;
import admin.adminsiteserver.qna.domain.Qna;
import admin.adminsiteserver.qna.domain.QnaRepository;
import admin.adminsiteserver.qna.domain.QuestionFilePath;
import admin.adminsiteserver.qna.exception.NotExistQnaException;
import admin.adminsiteserver.qna.ui.dto.BaseQnaRequest;
import admin.adminsiteserver.qna.ui.dto.UpdateQnaRequest;
import admin.adminsiteserver.qna.ui.dto.UploadQnaRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QnaService {
    private final QnaRepository qnaRepository;
    private final S3Uploader s3Uploader;
    private static final String QUESTION_IMAGE_PATH = "qna/question";

    @Transactional
    public QnaResponse upload(UploadQnaRequest request, @LoginUser LoginUserInfo loginUserInfo) {
        List<FilePathDto> filePathDtos = saveFiles(request);
        List<QuestionFilePath> imagePaths = getImagePathsFromDto(filePathDtos);
        Qna qna = request.createQna(loginUserInfo);
        qna.addQuestionImages(imagePaths);
        qnaRepository.save(qna);
        return QnaResponse.of(qna, filePathDtos);
    }

    @Transactional
    public QnaResponse update(UpdateQnaRequest request, LoginUserInfo loginUserInfo, Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(NotExistQnaException::new);
        qna.updateContentAndTitle(request.getTitle(), request.getContent());

        if (request.getImages() != null) {
            List<FilePathDto> filePathDtos = saveFiles(request);
            qna.addQuestionImages(getImagePathsFromDto(filePathDtos));
        }

        if (request.getDeleteFileUrls() != null) {
            qna.deleteImages(request.getDeleteFileUrls());
            s3Uploader.delete(request.getDeleteFileUrls());
        }

        return QnaResponse.of(qna, getImagePathDtosFromAnnouncement(qna));
    }

    @Transactional
    public void delete(Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(NotExistQnaException::new);

        List<String> deleteFileUrls = qna.getImages().stream()
                .map(QuestionFilePath::getFileUrl)
                .collect(Collectors.toList());
        s3Uploader.delete(deleteFileUrls);

        qnaRepository.delete(qna);
    }

    private List<FilePathDto> saveFiles(BaseQnaRequest request) {
        if (request.getImages() == null) {
            return null;
        }
        return s3Uploader.upload(request.getImages(), QUESTION_IMAGE_PATH);
    }

    private List<FilePathDto> getImagePathDtosFromAnnouncement(Qna qna) {
        return qna.getImages().stream()
                .map(FilePathDto::from)
                .collect(Collectors.toList());
    }

    private List<QuestionFilePath> getImagePathsFromDto(List<FilePathDto> imagePathDtos) {
        return imagePathDtos.stream()
                .map(filePathDto -> filePathDto.toFilePath(QuestionFilePath.class))
                .collect(Collectors.toList());
    }
}
