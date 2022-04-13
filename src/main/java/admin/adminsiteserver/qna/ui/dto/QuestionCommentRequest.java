package admin.adminsiteserver.qna.ui.dto;

import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.qna.domain.QuestionComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionCommentRequest {
    private String comment;

    public QuestionComment toQuestionComment(LoginUserInfo loginUserInfo) {
        return new QuestionComment(
                loginUserInfo.getUserId(),
                loginUserInfo.getName(),
                comment
        );
    }
}
