package com.ssafy.enjoytrips.service;

import com.ssafy.enjoytrips.model.dto.Comment;
import com.ssafy.enjoytrips.model.dto.Qna;
import com.ssafy.enjoytrips.model.dto.SearchCondition;

import java.util.List;

public interface QnaService {
    // 전체 조회
    List<Qna> list (SearchCondition searchCondition);
    // 개별 조회
    Qna select(int qnaId);
    // 글 작성
    int regist(Qna qna);
    // 글 수정
    int modify(Qna qna);
    // 글 삭제
    int delete(int qnaId);


}
