package com.ssafy.enjoytrips.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class QnaComment {

    private int qnaCommentId;
    private String qnaCommentContent;
    private int qnaCommentHarmful;
    private int qnaId;
    private String userId;

}
