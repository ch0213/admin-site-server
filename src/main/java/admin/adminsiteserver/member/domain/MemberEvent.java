package admin.adminsiteserver.member.domain;

import admin.adminsiteserver.common.domain.RoleType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class MemberEvent extends ApplicationEvent {
    private Long memberId;

    private MemberEventType eventType;

    private LocalDateTime eventDateTime;

    public MemberEvent(Object source, Long memberId, MemberEventType eventType) {
        super(source);
        this.memberId = memberId;
        this.eventType = eventType;
        this.eventDateTime = LocalDateTime.now();
    }

    public MemberEventHistory history() {
        return new MemberEventHistory(memberId, eventType, eventDateTime);
    }
}
