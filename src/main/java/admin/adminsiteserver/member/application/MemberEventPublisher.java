package admin.adminsiteserver.member.application;

import admin.adminsiteserver.common.vo.Author;

public interface MemberEventPublisher {
    void update(Author author);
}
