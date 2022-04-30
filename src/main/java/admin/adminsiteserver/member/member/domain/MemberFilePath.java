package admin.adminsiteserver.member.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class MemberFilePath {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String fileName;
    private String fileUrl;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberFilePath(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public void includedToMember(Member member) {
        this.member = member;
    }
}
