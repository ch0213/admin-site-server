package admin.adminsiteserver.qna.domain.answer;

import admin.adminsiteserver.qna.exception.NotExistAnswerException;
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
public class Answers {

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "qna_id")
    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

    public Answer findAnswer(Long answerId) {
        return answers.stream()
                .filter(answer -> answer.getId().equals(answerId))
                .findAny()
                .orElseThrow(NotExistAnswerException::new);
    }

    public void deleteAnswer(Answer answer) {
        answers.remove(answer);
    }
}
