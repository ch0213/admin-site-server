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
    private List<FilePathDto> images;
    private List<AnswerDto> answers;

    @Builder
    public QnaResponse(Long id, String authorId, String authorName, String title, String content, LocalDateTime createAt, LocalDateTime lastModifiedAt, List<FilePathDto> images, List<AnswerDto> answers) {
        this.id = id;
        this.authorId = authorId;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
        this.createAt = createAt;
        this.lastModifiedAt = lastModifiedAt;
        this.images = images;
        this.answers = answers;
    }

    public static QnaResponse from(Qna qna) {
        List<FilePathDto> filePathDtos = new ArrayList<>();
        List<AnswerDto> answerDtos = new ArrayList<>();
        if (qna.getFiles() != null) {
            filePathDtos = qna.getFiles().stream()
                    .map(FilePathDto::from)
                    .collect(Collectors.toList());
        }

        if (qna.getAnswers() != null) {
            answerDtos = qna.getAnswers().stream()
                    .map(AnswerDto::from)
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
                .answers(answerDtos)
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
