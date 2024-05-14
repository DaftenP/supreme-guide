package com.ssafy.enjoytrips.model.dao;

import com.ssafy.enjoytrips.model.dto.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeDao {
    // 전체 조회
    List<Notice> list();
    // 개별 조회
    Notice select(int noticeId);
    // 수정
    int modify(Notice notice);
    // 삭제
    int delete(int noticeId);
    // 등록
    int regist(Notice notice);
}
