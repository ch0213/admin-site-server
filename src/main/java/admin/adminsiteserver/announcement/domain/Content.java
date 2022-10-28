package admin.adminsiteserver.announcement.domain;

import admin.adminsiteserver.announcement.exception.CommentLengthException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
public class Content {
    private static final int MAX_LENGTH = 100;

    @Column
    private String content;

    public Content(String content) {
        if (content.length() > MAX_LENGTH) {
            throw new CommentLengthException();
        }
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Content content = (Content) o;
        return Objects.equals(getContent(), content.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContent());
    }
}
