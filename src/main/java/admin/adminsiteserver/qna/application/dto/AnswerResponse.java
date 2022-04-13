package admin.adminsiteserver.qna.application.dto;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.qna.domain.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class AnswerResponse {
    private Long id;
    private String authorId;
    private String authorName;
    private String content;
    private List<FilePathDto> files;
    private List<AnswerCommentResponse> comments;

    public static AnswerResponse from(Answer answer) {
        return new AnswerResponse(
                answer.getId(),
                answer.getAuthorId(),
                answer.getAuthorName(),
                answer.getContent(),
                answer.getFiles().stream()
                        .map(FilePathDto::from)
                        .collect(Collectors.toList()),
                answer.getComments().stream()
                        .map(AnswerCommentResponse::from)
                        .collect(Collectors.toList())
        );
    }
}
