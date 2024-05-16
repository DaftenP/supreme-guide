package com.ssafy.enjoytrips.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrips.model.dto.Comment;

@Mapper
public interface TripCommentDao {
    // 댓글 수정
    int modify(Comment comment);
    // 댓글 삭제
    int delete(int commentId);
    // 댓글 등록
    int regist(Comment comment);
}
