package com.ssafy.enjoytrips.service;

import com.ssafy.enjoytrips.model.dto.Comment;

public interface CommentService {
    // 등록
    int register(Comment comment);

    // 수정
    int modify(Comment comment);
    // 삭제
    int delete(int id);

    // 댓글 작성자 조회
    Comment selectCommentWriter(int qnaCommentId);

}
