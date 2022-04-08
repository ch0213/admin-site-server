package admin.adminsiteserver.levelup.application;

import admin.adminsiteserver.levelup.application.dto.LevelUpResponse;
import admin.adminsiteserver.levelup.domain.LevelUp;
import admin.adminsiteserver.levelup.domain.LevelUpRepository;
import admin.adminsiteserver.levelup.exception.NotAuthorizationLevelUpException;
import admin.adminsiteserver.levelup.exception.NotExistLevelUpException;
import admin.adminsiteserver.levelup.ui.dto.LevelUpRequest;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.member.member.domain.Member;
import admin.adminsiteserver.member.member.domain.MemberRepository;
import admin.adminsiteserver.member.member.exception.NotExistMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LevelUpService {

    private final MemberRepository memberRepository;
    private final LevelUpRepository levelUpRepository;

    @Transactional
    public LevelUpResponse registerLevelUp(LoginUserInfo loginUserInfo, LevelUpRequest request) {
        Member member = memberRepository.findByUserId(loginUserInfo.getUserId())
                .orElseThrow(NotExistMemberException::new);
        LevelUp savedLevelUp = levelUpRepository.save(request.from(member));
        return LevelUpResponse.from(savedLevelUp);
    }

    @Transactional
    public LevelUpResponse updateLevelUp(LoginUserInfo loginUserInfo, LevelUpRequest request, Long levelUpId) {
        LevelUp levelUp = levelUpRepository.findById(levelUpId)
                .orElseThrow(NotExistLevelUpException::new);
        validateAuthorization(loginUserInfo, levelUp);
        levelUp.updateRole(request.getRole());
        return LevelUpResponse.from(levelUp);
    }

    @Transactional
    public void deleteLevelUp(LoginUserInfo loginUserInfo, Long levelUpId) {
        LevelUp levelUp = levelUpRepository.findById(levelUpId)
                .orElseThrow(NotExistLevelUpException::new);
        validateAuthorization(loginUserInfo, levelUp);
        levelUpRepository.delete(levelUp);
    }

    private void validateAuthorization(LoginUserInfo loginUserInfo, LevelUp levelUp) {
        String requestUserId = loginUserInfo.getUserId();
        String authorUserId = levelUp.getMember().getUserId();
        if (!requestUserId.equals(authorUserId)) {
            throw new NotAuthorizationLevelUpException();
        }
    }
}
