package admin.adminsiteserver.gallery.domain;

import admin.adminsiteserver.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Gallery extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String authorId;
    private String authorName;
    private String title;

    @Lob
    private String content;

    @OneToMany(mappedBy = "gallery", cascade = ALL, orphanRemoval = true)
    private List<GalleryFilePath> images = new ArrayList<>();

    @Builder
    public Gallery(String authorId, String authorName, String title, String content) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
    }

    public void updateTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addImages(List<GalleryFilePath> newFilePaths) {
        if (newFilePaths == null) {
            return;
        }

        for (GalleryFilePath newFilePath : newFilePaths) {
            newFilePath.includedToGallery(this);
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
