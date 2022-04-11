package admin.adminsiteserver.qna.ui.dto;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.qna.domain.Answer;
import admin.adminsiteserver.qna.domain.AnswerFilePath;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerUploadRequest {
    private String content;
    private List<FilePathDto> files;

    public Answer createAnswer(LoginUserInfo loginUserInfo) {
        List<AnswerFilePath> filePaths = new ArrayList<>();
        if (getFiles() != null) {
            filePaths = getFiles().stream()
                    .map(filePathDto -> filePathDto.toFilePath(AnswerFilePath.class))
                    .collect(Collectors.toList());
        }

        return new Answer(loginUserInfo.getUserId(), loginUserInfo.getName(), content, filePaths);
    }
}
