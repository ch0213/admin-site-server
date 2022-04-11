package admin.adminsiteserver.gallery.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class GalleryFilePath {

    @Id @GeneratedValue
    private Long id;
    private String fileName;
    private String fileUrl;

    public GalleryFilePath(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}
