package com.ssafy.enjoytrips.service;

import com.ssafy.enjoytrips.model.dto.Notice;
import com.ssafy.enjoytrips.model.dto.SearchCondition;

import java.util.List;

public interface NoticeService {
    // 전체 조회
    List<Notice> list(SearchCondition searchCondition);
    // 개별 조회
    Notice select(int noticeId);
    // 글 작성
    int regist(Notice notice);
    // 글 수정
    int modify(Notice notice);
    // 글 삭제
    int delete(int noticeId);
    // 조회수 증가
    int updateView(int noticeId);
}
