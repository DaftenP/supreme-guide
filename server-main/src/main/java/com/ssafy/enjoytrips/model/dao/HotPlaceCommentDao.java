package com.ssafy.enjoytrips.model.dao;

import com.ssafy.enjoytrips.model.dto.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HotPlaceCommentDao {
    // 댓글 삭제
    int delete(int id);
    // 댓글 등록
    int regist(Comment hotplaceComment);
    // 댓글 수정
    int modify(Comment hotplaceComment);

    // 댓글 작성자 조회
    Comment selectCommentWriter(@Param("hotplaceCommentId") int hotplaceCommentId);
}
