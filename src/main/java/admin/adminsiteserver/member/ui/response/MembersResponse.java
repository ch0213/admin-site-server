package admin.adminsiteserver.member.ui.response;

import admin.adminsiteserver.common.response.PageInformation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MembersResponse {
    private List<MemberResponse> members;
    private PageInformation pageInfo;

    public static MembersResponse from(Page<MemberResponse> members) {
        return new MembersResponse(
                members.getContent(),
                PageInformation.from(members)
        );
    }
}
