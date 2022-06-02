package admin.adminsiteserver.qna.ui.dto;

import admin.adminsiteserver.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.qna.domain.QuestionFilePath;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseQnaRequest {
    @NotBlank(message = "제목은 빈칸일 수 없습니다.")
    private String title;

    @NotBlank(message = "내용은 빈칸일 수 없습니다.")
    private String content;
    private List<FilePathDto> files;

    public List<QuestionFilePath> toQuestionFilePaths() {
        if (files == null) return new ArrayList<>();
        return files.stream()
                .map(filePathDto -> filePathDto.toFilePath(QuestionFilePath.class))
                .collect(Collectors.toList());
    }
}
