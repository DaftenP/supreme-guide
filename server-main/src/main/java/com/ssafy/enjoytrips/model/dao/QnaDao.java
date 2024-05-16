package com.ssafy.enjoytrips.model.dao;

import com.ssafy.enjoytrips.model.dto.Comment;
import com.ssafy.enjoytrips.model.dto.Qna;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaDao {
    // 등록
    int regist(Qna qna);
    // 개별 조회
    Qna select(int qnaId);
    List<Comment> selectComment(int qnaId);
    // 전체조회
    List<Qna> list(SearchCondition searchCondition);
    // 수정
    int modify(Qna qna);
    // 삭제
    int delete(int qnaId);

}
