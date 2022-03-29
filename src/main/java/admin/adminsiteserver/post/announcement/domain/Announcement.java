package admin.adminsiteserver.post.announcement.domain;

import admin.adminsiteserver.common.domain.BaseTimeEntity;
import admin.adminsiteserver.common.domain.FilePath;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Announcement extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;
    private String authorId;
    private String authorName;
    private String title;
    private String content;

    @OneToMany(mappedBy = "announcement", cascade = ALL)
    private List<FilePath> images;

    @Builder
    public Announcement(String authorId, String authorName, String title, String content, List<FilePath> images) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
        this.images = images;
    }

    public void updateAnnouncement(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void saveImages(List<FilePath> images) {
        this.images = images;
        for (FilePath image : images) {
            image.includedToAnnouncement(this);
        }
    }
}
