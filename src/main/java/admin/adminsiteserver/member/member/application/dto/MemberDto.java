package admin.adminsiteserver.member.member.application.dto;

import admin.adminsiteserver.member.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class MemberDto {

    private String userId;
    private String email;
    private String name;
    private String studentNumber;
    private String phoneNumber;

    public static MemberDto from(Member member) {
        return new MemberDto(
                member.getUserId(),
                member.getEmail(),
                member.getName(),
                member.getStudentNumber(),
                member.getPhoneNumber()
        );
    }
}
