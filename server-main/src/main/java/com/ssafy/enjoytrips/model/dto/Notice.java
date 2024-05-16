package com.ssafy.enjoytrips.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Notice {

    private int noticeId;
    private String noticeTitle;
    private String noticeContent;
    private int noticeView;
    private String noticeWriter;
    private String noticeCreateDate;

}
