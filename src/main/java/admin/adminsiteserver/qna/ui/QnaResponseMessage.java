package admin.adminsiteserver.qna.ui;

import lombok.Getter;

@Getter
public enum QnaResponseMessage {
    QNA_UPLOAD_SUCCESS("질문 등록 성공"),
    QNA_UPDATE_SUCCESS("질문 수정 성공"),
    QNA_DELETE_SUCCESS("질문 삭제 성공"),
    ANSWER_UPLOAD_SUCCESS("답변 등록 성공");

    private final String message;

    QnaResponseMessage(String message) {
        this.message = message;
    }
}
