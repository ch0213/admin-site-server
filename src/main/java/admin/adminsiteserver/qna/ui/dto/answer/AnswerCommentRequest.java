package admin.adminsiteserver.qna.ui.dto.answer;

import admin.adminsiteserver.authentication.ui.LoginUserInfo;
import admin.adminsiteserver.qna.domain.answer.AnswerComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class AnswerCommentRequest {
    @NotNull(message = "댓글을 입력해주세요.")
    private String comment;

    public AnswerComment toAnswerComment(LoginUserInfo loginUserInfo) {
        return new AnswerComment(
                loginUserInfo.getEmail(),
                loginUserInfo.getName(),
                comment
        );
    }
}
