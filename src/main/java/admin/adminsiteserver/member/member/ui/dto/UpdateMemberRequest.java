package admin.adminsiteserver.member.member.ui.dto;

import admin.adminsiteserver.member.member.domain.Member;
import lombok.Getter;

@Getter
public class UpdateMemberRequest {
    private String email;
    private String name;
    private String studentNumber;
    private String phoneNumber;
}
