package com.ssafy.enjoytrips.model.dao;

import com.ssafy.enjoytrips.model.dto.HotPlace;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HotplaceDao {
    // 전체 조회
    List<HotPlace> list(SearchCondition searchCondition);
    // 개별 조회
    HotPlace select(int hotplaceId);
    // 글 등록
    int regist(HotPlace hotplace);
    // 글 수정
    int modify(HotPlace hotplace);
    // 글 삭제
    int delete(int hotplaceId);
    // 조회수 증가
    int updateView(int hotplaceId);

}
