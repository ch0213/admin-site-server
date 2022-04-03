package admin.adminsiteserver.announcement.domain;

import admin.adminsiteserver.member.member.domain.Member;
import admin.adminsiteserver.announcement.domain.Announcement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

import static lombok.AccessLevel.*;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class AnnouncementFilePath {

    @Id @GeneratedValue
    private Long id;
    private String fileName;
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;

    public AnnouncementFilePath(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public void includedToAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }
}