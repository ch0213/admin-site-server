package admin.adminsiteserver.levelup.ui;

import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.levelup.application.LevelUpService;
import admin.adminsiteserver.levelup.application.dto.LevelUpResponse;
import admin.adminsiteserver.levelup.ui.dto.LevelUpProcessRequest;
import admin.adminsiteserver.levelup.ui.dto.LevelUpRequest;
import admin.adminsiteserver.member.auth.util.LoginUser;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static admin.adminsiteserver.levelup.ui.LevelUpResponseMessage.*;

@RestController
@RequestMapping("/levelups")
@RequiredArgsConstructor
public class LevelUpController {

    private final LevelUpService levelUpService;

    @PostMapping
    public CommonResponse<LevelUpResponse> register(@LoginUser LoginUserInfo loginUserInfo, @RequestBody LevelUpRequest request) {
        LevelUpResponse levelUpResponse = levelUpService.registerLevelUp(loginUserInfo, request);
        return CommonResponse.of(levelUpResponse, LEVEL_UP_REGISTER_SUCCESS.getMessage());
    }

    @PutMapping("/{levelUpId}")
    public CommonResponse<LevelUpResponse> update(
            @LoginUser LoginUserInfo loginUserInfo,
            @RequestBody LevelUpRequest request,
            @PathVariable Long levelUpId
    ) {
        LevelUpResponse levelUpResponse = levelUpService.updateLevelUp(loginUserInfo, request, levelUpId);
        return CommonResponse.of(levelUpResponse, LEVEL_UP_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{levelUpId}")
    public CommonResponse<LevelUpResponse> delete(
            @LoginUser LoginUserInfo loginUserInfo,
            @PathVariable Long levelUpId
    ) {
        levelUpService.deleteLevelUp(loginUserInfo, levelUpId);
        return CommonResponse.from(LEVEL_UP_DELETE_SUCCESS.getMessage());
    }

    @GetMapping
    public CommonResponse<List<LevelUpResponse>> findNotProcessedLevelUp() {
        List<LevelUpResponse> notProcessedLevelUp = levelUpService.findAll();
        return CommonResponse.of(notProcessedLevelUp, LEVEL_UP_INQUIRE_SUCCESS.getMessage());
    }

    @PostMapping("/{levelUpId}/approve")
    public CommonResponse<Void> approveLevelUp(@PathVariable Long levelUpId) {
        levelUpService.updateMemberPosition(levelUpId);
        return CommonResponse.from(LEVEL_UP_APPROVE_SUCCESS.getMessage());
    }

    @PostMapping("/approve")
    public CommonResponse<Void> approveLevelUps(@RequestBody LevelUpProcessRequest request) {
        levelUpService.updateMembersPosition(request);
        return CommonResponse.from(LEVEL_UP_APPROVE_SUCCESS.getMessage());
    }

    @PostMapping("/{levelUpId}/reject")
    public CommonResponse<Void> rejectLevelUp(@PathVariable Long levelUpId) {
        levelUpService.rejectMemberPosition(levelUpId);
        return CommonResponse.from(LEVEL_UP_REJECT_SUCCESS.getMessage());
    }

    @PostMapping("/reject")
    public CommonResponse<Void> rejectLevelUps(@RequestBody LevelUpProcessRequest request) {
        levelUpService.rejectMembersPosition(request);
        return CommonResponse.from(LEVEL_UP_REJECT_SUCCESS.getMessage());
    }

    @GetMapping("/admin")
    public CommonResponse<Void> adminHeathCheck() {
        return CommonResponse.from("성공");
    }
}
