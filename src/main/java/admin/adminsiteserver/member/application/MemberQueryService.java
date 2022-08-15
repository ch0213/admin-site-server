package admin.adminsiteserver.member.application;

import admin.adminsiteserver.member.domain.Member;
import admin.adminsiteserver.member.dto.response.MemberResponse;
import admin.adminsiteserver.member.domain.MemberRepository;
import admin.adminsiteserver.member.dto.response.MembersResponse;
import admin.adminsiteserver.member.exception.NonExistentMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public MemberResponse findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(NonExistentMemberException::new);
        return MemberResponse.from(member);
    }

    public MembersResponse findMembers(Pageable pageable) {
        Page<MemberResponse> members = memberRepository.findAllByDeletedFalse(pageable)
                .map(MemberResponse::from);
        return MembersResponse.from(members);
    }
}
