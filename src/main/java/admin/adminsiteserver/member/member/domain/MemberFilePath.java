package admin.adminsiteserver.member.member.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class MemberFilePath {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String fileName;
    private String fileUrl;

    public MemberFilePath(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberFilePath that = (MemberFilePath) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getFileName(), that.getFileName()) && Objects.equals(getFileUrl(), that.getFileUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFileName(), getFileUrl());
    }
}
