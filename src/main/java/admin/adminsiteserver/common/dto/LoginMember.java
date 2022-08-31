package admin.adminsiteserver.common.dto;

import admin.adminsiteserver.authentication.domain.MemberAdapter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginMember {
    private final Long id;

    public static LoginMember from(MemberAdapter memberAdapter) {
        return new LoginMember(memberAdapter.getId());
    }
}
