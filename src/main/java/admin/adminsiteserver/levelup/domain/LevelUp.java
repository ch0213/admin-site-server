package admin.adminsiteserver.levelup.domain;

import admin.adminsiteserver.common.domain.BaseTimeEntity;
import admin.adminsiteserver.levelup.exception.NotExistRoleException;
import admin.adminsiteserver.member.member.domain.Member;
import admin.adminsiteserver.member.member.domain.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Arrays;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class LevelUp extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    private String role;

    private boolean processed;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public LevelUp(String role, boolean processed, Member member) {
        this.role = role;
        this.processed = processed;
        this.member = member;
    }

    public void updateRole(String newRole) {
        this.role = Arrays.stream(RoleType.values()).sequential()
                .filter(roleType -> roleType.getDescription().equals(newRole))
                .findAny()
                .orElseThrow(NotExistRoleException::new)
                .getDescription();
    }
}
