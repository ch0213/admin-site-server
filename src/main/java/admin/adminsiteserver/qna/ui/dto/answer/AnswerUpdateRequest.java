package admin.adminsiteserver.qna.ui.dto.answer;

import admin.adminsiteserver.aws.dto.response.FilePath;
import admin.adminsiteserver.qna.domain.answer.AnswerFilePath;
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
public class AnswerUpdateRequest {
    @NotBlank(message = "내용은 빈칸일 수 없습니다.")
    private String content;
    private List<FilePath> files;
    private List<FilePath> deleteFileUrls;

    public List<AnswerFilePath> toAnswerFilePaths() {
        if (files == null) return new ArrayList<>();
        return files.stream()
                .map(filePathDto -> filePathDto.toFilePath(AnswerFilePath.class))
                .collect(Collectors.toList());
    }
}
