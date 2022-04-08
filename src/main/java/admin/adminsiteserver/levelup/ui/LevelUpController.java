package admin.adminsiteserver.levelup.ui;

import admin.adminsiteserver.common.dto.CommonResponse;
import admin.adminsiteserver.levelup.application.LevelUpService;
import admin.adminsiteserver.levelup.application.dto.LevelUpResponse;
import admin.adminsiteserver.levelup.ui.dto.LevelUpRequest;
import admin.adminsiteserver.member.auth.util.LoginUser;
import admin.adminsiteserver.member.auth.util.dto.LoginUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static admin.adminsiteserver.levelup.ui.LevelUpResponseMessage.*;

@RestController
@RequestMapping("/levelup")
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
}
