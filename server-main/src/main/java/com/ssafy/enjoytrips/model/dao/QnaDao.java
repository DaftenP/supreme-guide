package com.ssafy.enjoytrips.model.dao;

import com.ssafy.enjoytrips.model.dto.Qna;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaDao {
    // 등록
    int regist(Qna qna);
    // 개별조회
    Qna select(int qnaId);
    // 전체조회
    List<Qna> list();
    // 수정
    int modify(Qna qna);
    // 삭제
    int delete(int qnaId);
}
