package com.ssafy.enjoytrips.model.dao;

import com.ssafy.enjoytrips.model.dto.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QnaCommentDao {
    // 댓글 수정
    int modify(Comment qnaComment);
    // 댓글 삭제
    int delete(int id);
    // 댓글 등록
    int regist(Comment qnaComment);

    // 댓글 작성자 조회
    // 댓글 작성자 조회
    Comment selectCommentWriter(@Param("qnaCommentId") int qnaCommentId);

}
