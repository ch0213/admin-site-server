package admin.adminsiteserver.announcement.domain;

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
public class AnnouncementFilePaths {

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "announcement_id")
    private List<AnnouncementFilePath> files = new ArrayList<>();

    public void saveFilePaths(List<AnnouncementFilePath> filePaths) {
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
