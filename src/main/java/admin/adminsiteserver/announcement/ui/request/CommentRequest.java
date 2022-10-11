package admin.adminsiteserver.announcement.ui.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CommentRequest {
    @NotNull(message = "댓글을 입력해주세요.")
    private String comment;
}
