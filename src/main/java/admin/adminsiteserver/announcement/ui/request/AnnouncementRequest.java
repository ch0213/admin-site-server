package admin.adminsiteserver.announcement.ui.request;

import admin.adminsiteserver.announcement.domain.Announcement;
import admin.adminsiteserver.announcement.domain.AnnouncementFilePath;
import admin.adminsiteserver.authentication.domain.LoginMember;
import admin.adminsiteserver.aws.dto.response.FilePath;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class AnnouncementRequest {
    @NotBlank(message = "제목은 빈칸일 수 없습니다.")
    private String title;

    @NotBlank(message = "내용은 빈칸일 수 없습니다.")
    private String content;

    private List<FilePath> files;

    public Announcement toEntity(LoginMember loginMember) {
        List<AnnouncementFilePath> filePaths = getAnnouncementFiles();
        return Announcement.builder()
                .author(loginMember.toAuthor())
                .title(getTitle())
                .content(getContent())
                .filePaths(filePaths)
                .build();
    }

    public List<AnnouncementFilePath> getAnnouncementFiles() {
        if (files == null) {
            return Collections.emptyList();
        }
        return files.stream()
                .map(it -> it.toFilePath(AnnouncementFilePath.class))
                .collect(Collectors.toList());
    }
}
