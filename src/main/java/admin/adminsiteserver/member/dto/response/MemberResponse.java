package admin.adminsiteserver.member.dto.response;

import admin.adminsiteserver.aws.dto.response.FilePath;
import admin.adminsiteserver.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class MemberResponse {

    private Long id;
    private String email;
    private String name;
    private String studentNumber;
    private String phoneNumber;
    private String role;
    private FilePath image;

    public static MemberResponse of(Member member, FilePath filePath) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getStudentNumber(),
                member.getPhoneNumber(),
                member.getRole().getDescription(),
                filePath
        );
    }

    public static MemberResponse from (Member member) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getStudentNumber(),
                member.getPhoneNumber(),
                member.getRole().getDescription(),
                FilePath.from(member.getFilePath())
        );
    }
}
