package admin.adminsiteserver.qna.domain;

import admin.adminsiteserver.aws.dto.response.FilePath;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class QuestionFilePaths {

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "qna_id")
    private List<QuestionFilePath> files = new ArrayList<>();

    public void saveFilePaths(List<QuestionFilePath> filePaths) {
        files.addAll(filePaths);
    }

    public void deleteFiles(List<FilePath> deleteFileUrls) {
        if (deleteFileUrls == null) return;
        files.removeIf(filePath -> deleteFileUrls.stream().map(FilePath::getFileUrl)
                .collect(Collectors.toList())
                .contains(filePath.getFileUrl()));
    }

    public List<FilePath> findDeleteFilePaths() {
        return files.stream()
                .map(FilePath::from)
                .collect(Collectors.toList());
    }
}
