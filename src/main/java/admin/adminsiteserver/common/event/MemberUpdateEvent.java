package admin.adminsiteserver.common.event;

import admin.adminsiteserver.common.vo.Author;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MemberUpdateEvent extends ApplicationEvent {
    private final Author author;

    public MemberUpdateEvent(Object source, Author author) {
        super(source);
        this.author = author;
    }
}
