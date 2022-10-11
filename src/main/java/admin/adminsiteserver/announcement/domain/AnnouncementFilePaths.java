package admin.adminsiteserver.announcement.domain;

import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor
public class AnnouncementFilePaths {
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "announcement_id")
    private List<AnnouncementFilePath> files = new ArrayList<>();

    public AnnouncementFilePaths(List<AnnouncementFilePath> files) {
        this.files.addAll(files);
    }

    public void update(List<AnnouncementFilePath> files) {
        this.files.clear();
        this.files.addAll(files);
    }

    public void deleteAll() {
        this.files.forEach(AnnouncementFilePath::delete);
    }

    public List<AnnouncementFilePath> getNotDeletedFiles() {
        return this.files.stream()
                .filter(AnnouncementFilePath::notDeleted)
                .collect(Collectors.toUnmodifiableList());
    }
}
