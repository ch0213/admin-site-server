package admin.adminsiteserver.qna.application.dto;

import admin.adminsiteserver.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.qna.application.dto.answer.AnswerResponse;
import admin.adminsiteserver.qna.domain.Qna;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QnaResponse {
    private Long id;
    private String authorEmail;
    private String authorName;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;
    private List<FilePathDto> files;
    private List<QuestionCommentResponse> comments;
    private List<AnswerResponse> answers;

    public static QnaResponse from(Qna qna) {
        return new QnaResponse(
                qna.getId(),
                qna.getAuthorEmail(),
                qna.getAuthorName(),
                qna.getTitle(),
                qna.getContent(),
                qna.getCreatedAt(),
                qna.getModifiedAt(),
                qna.getFiles().getFiles().stream()
                        .map(FilePathDto::from)
                        .collect(Collectors.toList()),
                qna.getComments().getComments().stream()
                        .map(QuestionCommentResponse::from)
                        .collect(Collectors.toList()),
                qna.getAnswers().getAnswers().stream()
                        .map(AnswerResponse::from)
                        .collect(Collectors.toList())
        );
    }
}
