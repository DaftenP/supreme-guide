package com.ssafy.enjoytrips.model.dao;

import com.ssafy.enjoytrips.model.dto.QnaComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaCommentDao {
    // 댓글 전체 조회
    List<QnaComment> list(int qnaId);
    // 댓글 수정
    int modify(QnaComment qnaComment);
    // 댓글 삭제
    int delete(int qnaCommentId);
    // 댓글 등록
    int regist(QnaComment qnaComment);
}
