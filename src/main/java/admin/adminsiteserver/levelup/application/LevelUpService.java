package admin.adminsiteserver.levelup.application;

import admin.adminsiteserver.levelup.application.dto.LevelUpResponse;
import admin.adminsiteserver.levelup.domain.LevelUp;
import admin.adminsiteserver.levelup.domain.LevelUpRepository;
import admin.adminsiteserver.levelup.exception.AlreadyExistLevelUpException;
import admin.adminsiteserver.levelup.exception.NotAuthorizationLevelUpException;
import admin.adminsiteserver.levelup.exception.NotExistLevelUpException;
import admin.adminsiteserver.levelup.ui.dto.LevelUpProcessRequest;
import admin.adminsiteserver.levelup.ui.dto.LevelUpRequest;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import admin.adminsiteserver.member.member.domain.Member;
import admin.adminsiteserver.member.member.domain.MemberRepository;
import admin.adminsiteserver.member.member.exception.NotExistMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LevelUpService {

    private final MemberRepository memberRepository;
    private final LevelUpRepository levelUpRepository;

    @Transactional
    public LevelUpResponse registerLevelUp(LoginUserInfo loginUserInfo, LevelUpRequest request) {
        Member member = memberRepository.findByEmail(loginUserInfo.getEmail())
                .orElseThrow(NotExistMemberException::new);
        levelUpRepository.findByUserEmailAndProcessedIsFalse(loginUserInfo.getEmail())
                .ifPresent(levelUp -> {throw new AlreadyExistLevelUpException();});
        LevelUp savedLevelUp = levelUpRepository.save(request.from(member));
        return LevelUpResponse.from(savedLevelUp);
    }

    @Transactional
    public LevelUpResponse updateLevelUp(LoginUserInfo loginUserInfo, LevelUpRequest request, Long levelUpId) {
        LevelUp levelUp = levelUpRepository.findByIdAndProcessedIsFalse(levelUpId)
                .orElseThrow(NotExistLevelUpException::new);
        validateAuthorization(loginUserInfo, levelUp);
        levelUp.updateRole(request.getRole());
        return LevelUpResponse.from(levelUp);
    }

    @Transactional
    public void deleteLevelUp(LoginUserInfo loginUserInfo, Long levelUpId) {
        LevelUp levelUp = levelUpRepository.findByIdAndProcessedIsFalse(levelUpId)
                .orElseThrow(NotExistLevelUpException::new);
        validateAuthorization(loginUserInfo, levelUp);
        levelUpRepository.delete(levelUp);
    }

    public List<LevelUpResponse> findAll() {
        return levelUpRepository.findAllByProcessedIsFalse().stream()
                .map(LevelUpResponse::from)
                .collect(Collectors.toList());
    }

    private void validateAuthorization(LoginUserInfo loginUserInfo, LevelUp levelUp) {
        if (loginUserInfo.isNotEqualUser(levelUp.registerUserId())) {
            throw new NotAuthorizationLevelUpException();
        }
    }

    @Transactional
    public void updateMemberPosition(Long levelUpId) {
        LevelUp levelUp = levelUpRepository.findById(levelUpId)
                .orElseThrow(NotExistLevelUpException::new);
        levelUp.approve();
    }

    @Transactional
    public void updateMembersPosition(LevelUpProcessRequest request) {
        List<LevelUp> existLevelUp = levelUpRepository.findAllById(request.getLevelUpIds());
        for (LevelUp levelUp : existLevelUp) {
            updateMemberPosition(levelUp.getId());
        }
    }

    @Transactional
    public void rejectMemberPosition(Long levelUpId) {
        LevelUp levelUp = levelUpRepository.findById(levelUpId)
                .orElseThrow(NotExistLevelUpException::new);
        levelUp.reject();
    }

    @Transactional
    public void rejectMembersPosition(LevelUpProcessRequest request) {
        List<LevelUp> existLevelUp = levelUpRepository.findAllById(request.getLevelUpIds());
        for (LevelUp levelUp : existLevelUp) {
            rejectMemberPosition(levelUp.getId());
        }
    }
}
