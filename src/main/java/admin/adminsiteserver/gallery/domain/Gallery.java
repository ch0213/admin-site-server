package admin.adminsiteserver.gallery.domain;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "gallery_id")
    private List<GalleryFilePath> files = new ArrayList<>();

    @Builder
    public Gallery(String authorId, String authorName, String title, String content, List<GalleryFilePath> files) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
        this.files = files;
    }

    public void updateTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void deleteFiles(List<FilePathDto> deleteFileUrls) {
        files.removeIf(filePath -> deleteFileUrls.stream().map(FilePathDto::getFileUrl)
                .collect(Collectors.toList())
                .contains(filePath.getFileUrl()));
    }
}
