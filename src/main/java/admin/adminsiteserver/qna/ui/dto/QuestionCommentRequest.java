package admin.adminsiteserver.qna.ui.dto;

import admin.adminsiteserver.authentication.ui.LoginUserInfo;
import admin.adminsiteserver.qna.domain.QuestionComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class QuestionCommentRequest {
    @NotNull(message = "댓글을 입력해주세요.")
    private String comment;

    public QuestionComment toQuestionComment(LoginUserInfo loginUserInfo) {
        return new QuestionComment(
                loginUserInfo.getEmail(),
                loginUserInfo.getName(),
                comment
        );
    }
}
