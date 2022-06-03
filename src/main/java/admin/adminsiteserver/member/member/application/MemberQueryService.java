package admin.adminsiteserver.member.member.application;

import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.common.dto.PageInfo;
import admin.adminsiteserver.member.member.application.dto.MemberResponse;
import admin.adminsiteserver.member.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static admin.adminsiteserver.member.member.ui.MemberResponseMessage.INQUIRE_SUCCESS;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public CommonResponse<List<MemberResponse>> findMembers(Pageable pageable) {
        Page<MemberResponse> members = memberRepository.findAllByDeletedFalse(pageable)
                .map(MemberResponse::from);
        return CommonResponse.of(members.getContent(), PageInfo.from(members), INQUIRE_SUCCESS.getMessage());
    }
}
