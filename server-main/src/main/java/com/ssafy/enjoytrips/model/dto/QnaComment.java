package com.ssafy.enjoytrips.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaComment {

    private int qnaCommentId;
    private String qnaCommentContent;
    private int qnaCommentHarmful;
    private int qnaId;
    private String userId;

    public QnaComment() {
    }

    public QnaComment(int qnaCommentId, String qnaCommentContent, int qnaCommentHarmful, int qnaId, String userId) {
        this.qnaCommentId = qnaCommentId;
        this.qnaCommentContent = qnaCommentContent;
        this.qnaCommentHarmful = qnaCommentHarmful;
        this.qnaId = qnaId;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "QnaComment{" +
                "qnaCommentId=" + qnaCommentId +
                ", qnaCommentContent='" + qnaCommentContent + '\'' +
                ", qnaCommentHarmful=" + qnaCommentHarmful +
                ", qnaId=" + qnaId +
                ", userId='" + userId + '\'' +
                '}';
    }
}
