package admin.adminsiteserver.member.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
public class MemberFilePath {
    private String fileName;
    private String fileUrl;

    public MemberFilePath(String fileName, String fileUrl) {
        if (fileName.isBlank() || fileUrl.isBlank()) {
            throw new IllegalArgumentException();
        }

        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberFilePath that = (MemberFilePath) o;
        return Objects.equals(getFileName(), that.getFileName()) && Objects.equals(getFileUrl(), that.getFileUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFileName(), getFileUrl());
    }
}
