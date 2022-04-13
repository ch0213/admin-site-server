package admin.adminsiteserver.qna.ui.dto;

import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.qna.domain.AnswerComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerCommentRequest {
    private String comment;

    public AnswerComment toAnswerComment(LoginUserInfo loginUserInfo) {
        return new AnswerComment(
                loginUserInfo.getUserId(),
                loginUserInfo.getName(),
                comment
        );
    }
}
