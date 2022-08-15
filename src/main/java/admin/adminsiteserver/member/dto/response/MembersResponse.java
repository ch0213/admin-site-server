package admin.adminsiteserver.member.dto.response;

import admin.adminsiteserver.common.dto.PageInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MembersResponse {
    private List<MemberResponse> memberResponses;
    private PageInfo pageInfo;

    public static MembersResponse from(Page<MemberResponse> members) {
        return new MembersResponse(
                members.getContent(),
                PageInfo.from(members)
        );
    }
}
