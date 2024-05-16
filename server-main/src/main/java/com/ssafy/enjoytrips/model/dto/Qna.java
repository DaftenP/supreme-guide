package com.ssafy.enjoytrips.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Qna {
    private int qnaId;
    private String qnaTitle;
    private String qnaContent;
    private int qnaView;
    private String qnaWriter;
    private String qnaCreatetime;

}
