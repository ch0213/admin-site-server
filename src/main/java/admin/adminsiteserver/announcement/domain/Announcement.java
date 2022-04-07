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
    private List<AnnouncementFilePath> images = new ArrayList<>();

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

    public void addImages(List<AnnouncementFilePath> newFilePaths) {
        if (newFilePaths == null) {
            return;
        }

        for (AnnouncementFilePath newFilePath : newFilePaths) {
            newFilePath.includedToAnnouncement(this);
            this.images.add(newFilePath);
        }
    }

    public void deleteImages(List<String> deleteFileUrls) {
        if (deleteFileUrls == null) {
            return;
        }
        images.removeIf(filePath -> deleteFileUrls.contains(filePath.getFileUrl()));
    }
}
