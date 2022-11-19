package admin.adminsiteserver.authentication.domain;

import admin.adminsiteserver.common.domain.RoleType;

@FunctionalInterface
public interface AuthorConstructor<T> {
    T author(Long id, String name, String studentNumber, RoleType roleType);
}
