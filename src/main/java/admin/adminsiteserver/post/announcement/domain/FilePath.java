package admin.adminsiteserver.post.announcement.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class FilePath {

    @Id @GeneratedValue
    private Long id;
    private String fileName;
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;

    public FilePath(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public void includedToAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }
}
