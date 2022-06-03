package admin.adminsiteserver.member.member.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberResponseMessage {
    SIGNUP_SUCCESS("회원가입 성공"),
    UPDATE_SUCCESS("회원정보 수정 성공"),
    DELETE_SUCCESS("회원탈퇴 성공"),
    INQUIRE_MYSELF_SUCCESS("본인 정보 조회 성공"),
    INQUIRE_SUCCESS("회원 목록 조회 성공");

    private final String message;
}
