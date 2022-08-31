package admin.adminsiteserver.levelup.domain;

import admin.adminsiteserver.common.domain.BaseTimeEntity;
import admin.adminsiteserver.member.domain.Member;
import admin.adminsiteserver.member.domain.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class LevelUp extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String userEmail;

    @Enumerated(STRING)
    private RoleType role;

    private boolean processed;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public LevelUp(String userEmail, String role, boolean processed, Member member) {
        this.userEmail = userEmail;
        this.role = RoleType.matchRole(role);
        this.processed = processed;
        this.member = member;
    }

    public void updateRole(String newRole) {
        this.role = RoleType.matchRole(newRole);
    }

    public String registerUserId() {
        return member.getEmail();
    }

    public void approve() {
        member.updateRole(role);
        this.processed = true;
    }

    public void reject() {
        this.processed = true;
    }
}
