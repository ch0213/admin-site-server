package admin.adminsiteserver.qna.application.dto;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.qna.domain.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AnswerDto {
    private Long id;
    private String authorId;
    private String authorName;
    private String title;
    private String content;
    private List<FilePathDto> images;

    public static AnswerDto of(Answer answer, List<FilePathDto> images) {
        return new AnswerDto(
                answer.getId(),
                answer.getAuthorId(),
                answer.getAuthorName(),
                answer.getTitle(),
                answer.getContent(),
                images
        );
    }
}
