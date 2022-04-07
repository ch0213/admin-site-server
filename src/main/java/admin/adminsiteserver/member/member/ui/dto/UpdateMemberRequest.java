package admin.adminsiteserver.member.member.ui.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UpdateMemberRequest {
    private String email;
    private String name;
    private String studentNumber;
    private String phoneNumber;
    private MultipartFile image;
}
