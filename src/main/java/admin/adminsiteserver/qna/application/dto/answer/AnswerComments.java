package admin.adminsiteserver.qna.application.dto.answer;

import admin.adminsiteserver.qna.domain.answer.AnswerComment;
import admin.adminsiteserver.qna.exception.NotExistAnswerCommentException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Getter
@Embeddable
@NoArgsConstructor
public class AnswerComments {

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "answer_id")
    private List<AnswerComment> comments = new ArrayList<>();

    public void addComment(AnswerComment comment) {
        this.comments.add(comment);
    }

    public AnswerComment findUpdateComment(Long commentId) {
        return comments.stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findAny()
                .orElseThrow(NotExistAnswerCommentException::new);
    }

    public void deleteComment(AnswerComment comment) {
        comments.remove(comment);
    }
}
