package com.ssafy.enjoytrips.service;

import com.ssafy.enjoytrips.model.dto.QnaComment;

import java.util.List;

public interface CommentService {
    // 등록
    int register(QnaComment qnaComment);
    // 전체 조회
    List<QnaComment> list(int qnaId);
    // 수정
    int modify(QnaComment qnaComment);
    // 삭제
    int delete(int qnaId);
}
