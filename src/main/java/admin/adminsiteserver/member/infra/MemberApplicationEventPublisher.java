package admin.adminsiteserver.member.infra;

import admin.adminsiteserver.common.event.MemberUpdateEvent;
import admin.adminsiteserver.common.vo.Author;
import admin.adminsiteserver.member.application.MemberEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberApplicationEventPublisher implements MemberEventPublisher {
    private final ApplicationEventPublisher publisher;

    @Override
    public void update(Author author) {
        publisher.publishEvent(new MemberUpdateEvent(this, author));
    }
}
