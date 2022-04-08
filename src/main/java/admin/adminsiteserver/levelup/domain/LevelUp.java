package admin.adminsiteserver.levelup.domain;

import admin.adminsiteserver.common.domain.BaseTimeEntity;
import admin.adminsiteserver.member.member.domain.Member;
import admin.adminsiteserver.member.member.domain.RoleType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class LevelUp extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @Enumerated(STRING)
    private RoleType role;

    private boolean processed;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public LevelUp(RoleType role, boolean processed, Member member) {
        this.role = role;
        this.processed = processed;
        this.member = member;
    }
}
