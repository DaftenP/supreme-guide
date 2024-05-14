package com.ssafy.enjoytrips.model.dao;

import com.ssafy.enjoytrips.model.dto.QnaComment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QnaCommentDao {
    // 댓글 조회

    // 댓글 수정
    int modify(QnaComment qnaComment);
    // 댓글 삭제
    int delete(int qnaCommentId);
}
