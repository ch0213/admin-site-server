package admin.adminsiteserver.announcement.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class AnnouncementFilePath {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String fileName;
    private String fileUrl;
    private boolean deleted;

    public AnnouncementFilePath(String fileName, String fileUrl) {
        this(null, fileName, fileUrl);
    }

    public AnnouncementFilePath(Long id, String fileName, String fileUrl) {
        this.id = id;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.deleted = false;
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean notDeleted() {
        return !this.deleted;
    }
}
