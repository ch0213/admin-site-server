package admin.adminsiteserver.qna.domain;

import admin.adminsiteserver.qna.exception.NotExistQuestionCommentException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class QuestionComments {

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "qna_id")
    private List<QuestionComment> comments = new ArrayList<>();

    public void addComment(QuestionComment comment) {
        this.comments.add(comment);
    }

    public QuestionComment findUpdateComment(Long commentId) {
        return comments.stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findAny()
                .orElseThrow(NotExistQuestionCommentException::new);
    }

    public void deleteComment(QuestionComment comment) {
        comments.remove(comment);
    }
}
