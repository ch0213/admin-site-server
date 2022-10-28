package admin.adminsiteserver.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEventHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private MemberEventType eventType;

    private LocalDateTime eventDateTime;

    private boolean processed;

    public MemberEventHistory(Long memberId, MemberEventType eventType, LocalDateTime eventDateTime) {
        this.memberId = memberId;
        this.eventType = eventType;
        this.eventDateTime = eventDateTime;
        this.processed = false;
    }

    public void process() {
        this.processed = true;
    }
}
