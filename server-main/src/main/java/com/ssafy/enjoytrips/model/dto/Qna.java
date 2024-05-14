package com.ssafy.enjoytrips.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Qna {
    private int qnaId;
    private String qnaTitle;
    private String qnaContent;
    private int qnaView;
    private String qnaWriter;
    private String qnaCreatetime;

    public Qna() {
    }

    public Qna(int qnaId, String qnaTitle, String qnaContent, int qnaView, String qnaWriter, String qnaCreatetime) {
        this.qnaId = qnaId;
        this.qnaTitle = qnaTitle;
        this.qnaContent = qnaContent;
        this.qnaView = qnaView;
        this.qnaWriter = qnaWriter;
        this.qnaCreatetime = qnaCreatetime;
    }

    @Override
    public String toString() {
        return "Qna{" +
                "qnaId=" + qnaId +
                ", qnaTitle='" + qnaTitle + '\'' +
                ", qnaContent='" + qnaContent + '\'' +
                ", qnaView=" + qnaView +
                ", qnaWriter='" + qnaWriter + '\'' +
                ", qnaCreatetime='" + qnaCreatetime + '\'' +
                '}';
    }
}
