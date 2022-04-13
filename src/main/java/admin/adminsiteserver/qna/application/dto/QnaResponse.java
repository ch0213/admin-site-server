package admin.adminsiteserver.qna.application.dto;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.qna.domain.Qna;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@Getter
@JsonInclude(NON_NULL)
public class QnaResponse {
    private Long id;
    private String authorId;
    private String authorName;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;
    private List<FilePathDto> files;
    private List<AnswerResponse> answers;
    private List<QuestionCommentResponse> comments;

    @Builder
    public QnaResponse(Long id, String authorId, String authorName, String title, String content, LocalDateTime createAt, LocalDateTime lastModifiedAt, List<FilePathDto> images, List<AnswerResponse> answers, List<QuestionCommentResponse> comments) {
        this.id = id;
        this.authorId = authorId;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
        this.createAt = createAt;
        this.lastModifiedAt = lastModifiedAt;
        this.files = images;
        this.answers = answers;
        this.comments = comments;
    }

    public static QnaResponse from(Qna qna) {
        List<FilePathDto> filePathDtos = new ArrayList<>();
        List<AnswerResponse> answerResponses = new ArrayList<>();
        List<QuestionCommentResponse> commentResponses = new ArrayList<>();
        if (qna.getFiles() != null) {
            filePathDtos = qna.getFiles().stream()
                    .map(FilePathDto::from)
                    .collect(Collectors.toList());
        }

        if (qna.getAnswers() != null) {
            answerResponses = qna.getAnswers().stream()
                    .map(AnswerResponse::from)
                    .collect(Collectors.toList());
        }

        if (qna.getComments() != null) {
            commentResponses = qna.getComments().stream()
                    .map(QuestionCommentResponse::from)
                    .collect(Collectors.toList());
        }

        return QnaResponse.builder()
                .id(qna.getId())
                .authorId(qna.getAuthorId())
                .authorName(qna.getAuthorName())
                .title(qna.getTitle())
                .content(qna.getContent())
                .createAt(qna.getCreatedAt())
                .lastModifiedAt(qna.getModifiedAt())
                .images(filePathDtos)
                .answers(answerResponses)
                .comments(commentResponses)
                .build();
    }

    public static QnaResponse toInstanceOfList(Qna qna) {
        List<FilePathDto> filePathDtos = new ArrayList<>();
        if (qna.getFiles() != null) {
            filePathDtos = qna.getFiles().stream()
                    .map(FilePathDto::from)
                    .collect(Collectors.toList());
        }

        return QnaResponse.builder()
                .id(qna.getId())
                .authorId(qna.getAuthorId())
                .authorName(qna.getAuthorName())
                .title(qna.getTitle())
                .content(qna.getContent())
                .createAt(qna.getCreatedAt())
                .lastModifiedAt(qna.getModifiedAt())
                .images(filePathDtos)
                .build();
    }
}
