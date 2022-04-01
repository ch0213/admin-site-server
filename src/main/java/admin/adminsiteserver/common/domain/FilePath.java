package admin.adminsiteserver.common.domain;

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
public class FilePath {

    @Id @GeneratedValue
    private Long id;
    private String fileName;
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public FilePath(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public void includedToAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }

    public void includedToMember(Member member) {
        this.member = member;
    }
}
