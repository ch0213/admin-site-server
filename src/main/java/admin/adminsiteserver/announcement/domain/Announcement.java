package admin.adminsiteserver.announcement.domain;

import admin.adminsiteserver.common.domain.BaseTimeEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static lombok.AccessLevel.*;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Announcement extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String authorId;
    private String authorName;
    private String title;

    @Lob
    private String content;

    @OneToMany(mappedBy = "announcement", cascade = ALL, orphanRemoval = true)
    private List<AnnouncementFilePath> files = new ArrayList<>();

    @Builder
    public Announcement(String authorId, String authorName, String title, String content) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
    }

    public void updateTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addFiles(List<AnnouncementFilePath> newFilePaths) {
        if (newFilePaths == null) {
            return;
        }

        for (AnnouncementFilePath newFilePath : newFilePaths) {
            newFilePath.includedToAnnouncement(this);
            this.files.add(newFilePath);
        }
    }

    public void deleteFiles(List<String> deleteFileUrls) {
        if (deleteFileUrls == null) {
            return;
        }
        files.removeIf(filePath -> deleteFileUrls.contains(filePath.getFileUrl()));
    }
}
