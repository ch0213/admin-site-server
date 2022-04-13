package admin.adminsiteserver.qna.ui;

import lombok.Getter;

@Getter
public enum QnaResponseMessage {
    QNA_UPLOAD_SUCCESS("질문 등록 성공"),
    QNA_UPDATE_SUCCESS("질문 수정 성공"),
    QNA_DELETE_SUCCESS("질문 삭제 성공"),
    ANSWER_UPLOAD_SUCCESS("답변 등록 성공"),
    ANSWER_UPDATE_SUCCESS("답변 수정 성공"),
    ANSWER_DELETE_SUCCESS("답변 삭제 성공"),
    INQUIRE_QNA_SUCCESS("질문 조회 성공"),
    INQUIRE_QNA_LIST_SUCCESS("질문 목록 조회 성공"),
    QNA_COMMENT_UPLOAD_SUCCESS("질문 댓글 등록 성공"),
    QNA_COMMENT_UPDATE_SUCCESS("질문 댓글 수정 성공"),
    QNA_COMMENT_DELETE_SUCCESS("질문 댓글 삭제 성공"),
    ANSWER_COMMENT_UPLOAD_SUCCESS("답변 댓글 등록 성공"),
    ANSWER_COMMENT_UPDATE_SUCCESS("답변 댓글 수정 성공"),
    ANSWER_COMMENT_DELETE_SUCCESS("답변 댓글 삭제 성공");

    private final String message;

    QnaResponseMessage(String message) {
        this.message = message;
    }
}
