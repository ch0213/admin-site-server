package admin.adminsiteserver.member.member.application.dto;

import admin.adminsiteserver.common.aws.infrastructure.dto.FilePathDto;
import admin.adminsiteserver.member.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDto {

    private Long id;
    private String userId;
    private String email;
    private String name;
    private String studentNumber;
    private String phoneNumber;
    private String role;
    private FilePathDto filePathDto;

    public static MemberDto of(Member member, FilePathDto filePathDto) {
        return new MemberDto(
                member.getId(),
                member.getUserId(),
                member.getEmail(),
                member.getName(),
                member.getStudentNumber(),
                member.getPhoneNumber(),
                member.getRole().getDescription(),
                filePathDto
        );
    }
}
