package admin.adminsiteserver.qna.ui.dto;

import admin.adminsiteserver.announcement.domain.AnnouncementFilePath;
import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.qna.domain.Qna;
import admin.adminsiteserver.qna.domain.QuestionFilePath;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class UploadQnaRequest extends BaseQnaRequest{
    public UploadQnaRequest(String title, String content, List<FilePathDto> images) {
        super(title, content, images);
    }

    public Qna createQna(LoginUserInfo loginUserInfo) {
        List<QuestionFilePath> filePaths = new ArrayList<>();
        if (getFiles() != null) {
            filePaths = getFiles().stream()
                    .map(filePathDto -> filePathDto.toFilePath(QuestionFilePath.class))
                    .collect(Collectors.toList());
        }

        return Qna.builder()
                .authorId(loginUserInfo.getUserId())
                .authorName(loginUserInfo.getName())
                .title(getTitle())
                .content(getContent())
                .files(filePaths)
                .build();
    }
}
