package admin.adminsiteserver.announcement.domain;

import admin.adminsiteserver.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class AnnouncementComment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String authorEmail;
    private String authorName;
    private String comment;

    public AnnouncementComment(String authorEmail, String authorName, String comment) {
        this.authorEmail = authorEmail;
        this.authorName = authorName;
        this.comment = comment;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }
}
