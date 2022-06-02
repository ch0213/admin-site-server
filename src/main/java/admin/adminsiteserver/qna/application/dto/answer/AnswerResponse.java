package admin.adminsiteserver.qna.application.dto.answer;

import admin.adminsiteserver.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.qna.domain.answer.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponse {
    private Long id;
    private String authorId;
    private String authorName;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;
    private List<FilePathDto> files;
    private List<AnswerCommentResponse> comments;

    public static AnswerResponse from(Answer answer) {
        return new AnswerResponse(
                answer.getId(),
                answer.getAuthorEmail(),
                answer.getAuthorName(),
                answer.getContent(),
                answer.getCreatedAt(),
                answer.getModifiedAt(),
                answer.getFiles().getFiles().stream()
                        .map(FilePathDto::from)
                        .collect(Collectors.toList()),
                answer.getComments().getComments().stream()
                        .map(AnswerCommentResponse::from)
                        .collect(Collectors.toList())
        );
    }
}
