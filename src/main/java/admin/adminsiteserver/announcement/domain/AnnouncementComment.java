package admin.adminsiteserver.announcement.domain;

import admin.adminsiteserver.common.domain.BaseTimeEntity;
import admin.adminsiteserver.member.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class AnnouncementComment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String comment;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public AnnouncementComment(String comment, Member member) {
        this.comment = comment;
        this.member = member;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }
}
