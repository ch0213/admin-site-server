package admin.adminsiteserver.qna.domain.answer;

import admin.adminsiteserver.aws.infrastructure.dto.FilePathDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;

@Getter
@Embeddable
@NoArgsConstructor
public class AnswerFilePaths {

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "answer_id")
    private List<AnswerFilePath> files = new ArrayList<>();

    public void saveFilePaths(List<AnswerFilePath> filePaths) {
        files.addAll(filePaths);
    }

    public void deleteFiles(List<FilePathDto> deleteFileUrls) {
        if (deleteFileUrls == null) return;
        files.removeIf(filePath -> deleteFileUrls.stream().map(FilePathDto::getFileUrl)
                .collect(Collectors.toList())
                .contains(filePath.getFileUrl()));
    }

    public List<FilePathDto> findDeleteFilePaths() {
        return files.stream()
                .map(FilePathDto::from)
                .collect(Collectors.toList());
    }
}
