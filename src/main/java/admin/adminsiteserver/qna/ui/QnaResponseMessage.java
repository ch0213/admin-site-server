package admin.adminsiteserver.qna.ui;

import lombok.Getter;

@Getter
public enum QnaResponseMessage {
    Qna_UPLOAD_SUCCESS("질문 등록 성공");

    private final String message;

    QnaResponseMessage(String message) {
        this.message = message;
    }
}
