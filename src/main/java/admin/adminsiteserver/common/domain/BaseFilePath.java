package admin.adminsiteserver.common.domain;

import lombok.Getter;

import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public class BaseFilePath {
    private boolean deleted;
    private boolean deletedFromStorage;

    public void delete() {
        this.deleted = true;
    }

    public void deleteFromStorage() {
        this.deletedFromStorage = true;
    }
}
