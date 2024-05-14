package com.ssafy.enjoytrips.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notice {

    private int noticeId;
    private String noticeTitle;
    private String noticeContent;
    private int noticeView;
    private String noticeWriter;
    private String noticeCreateDate;

    public Notice() {
    }

    public Notice(int noticeId, String noticeTitle, String noticeContent, int noticeView, String noticeWriter, String noticeCreateDate) {
        this.noticeId = noticeId;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.noticeView = noticeView;
        this.noticeWriter = noticeWriter;
        this.noticeCreateDate = noticeCreateDate;
    }
}
