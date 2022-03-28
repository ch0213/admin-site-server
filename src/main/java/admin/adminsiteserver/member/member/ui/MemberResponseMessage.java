package admin.adminsiteserver.member.member.ui;

import lombok.Getter;

@Getter
public enum MemberResponseMessage {
    SIGNUP_SUCCESS("회원가입 성공"),
    UPDATE_SUCCESS("회원정보 수정 성공");

    private final String message;

    MemberResponseMessage(String message) {
        this.message = message;
    }
}
