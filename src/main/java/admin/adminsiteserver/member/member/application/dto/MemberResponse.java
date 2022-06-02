package admin.adminsiteserver.member.member.application.dto;

import admin.adminsiteserver.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.member.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class MemberResponse {

    private Long id;
    private String email;
    private String name;
    private String studentNumber;
    private String phoneNumber;
    private String role;
    private FilePathDto profileImage;

    public static MemberResponse of(Member member, FilePathDto filePathDto) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getStudentNumber(),
                member.getPhoneNumber(),
                member.getRole().getDescription(),
                filePathDto
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
                FilePathDto.from(member.getFilePath())
        );
    }
}
