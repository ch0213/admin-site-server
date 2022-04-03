package admin.adminsiteserver.qna.application;

import admin.adminsiteserver.common.aws.infrastructure.S3Uploader;
import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.member.auth.util.LoginUser;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.qna.application.dto.QnaResponse;
import admin.adminsiteserver.qna.domain.Qna;
import admin.adminsiteserver.qna.domain.QnaRepository;
import admin.adminsiteserver.qna.domain.QuestionFilePath;
import admin.adminsiteserver.qna.ui.dto.UploadQnaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QnaService {
    private final QnaRepository qnaRepository;
    private final S3Uploader s3Uploader;
    private static final String QUESTION_IMAGE_PATH = "qna/question";

    @Transactional
    public QnaResponse upload(UploadQnaRequest request, @LoginUser LoginUserInfo loginUserInfo) {
        List<QuestionFilePath> imagePaths = saveFilesAndGetImagePaths(request);
        Qna qna = createQna(request, loginUserInfo);
        qna.addQuestionImages(imagePaths);
        qnaRepository.save(qna);
        return QnaResponse.of(qna, getImagePathDtosFromQna(qna));
    }

    private List<FilePathDto> getImagePathDtosFromQna(Qna qna) {
        return qna.getImages().stream()
                .map(FilePathDto::from)
                .collect(Collectors.toList());
    }

    private List<QuestionFilePath> saveFilesAndGetImagePaths(UploadQnaRequest request) {
        if (request.getImages() == null) {
            return null;
        }
        List<FilePathDto> imagePathDtos = s3Uploader.upload(request.getImages(), QUESTION_IMAGE_PATH);
        return imagePathDtos.stream()
                .map(filePathDto -> filePathDto.toFilePath(QuestionFilePath.class))
                .collect(Collectors.toList());
    }

    private Qna createQna(UploadQnaRequest request, LoginUserInfo loginUserInfo) {
        return Qna.builder()
                .authorId(loginUserInfo.getUserId())
                .authorName(loginUserInfo.getName())
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }
}
