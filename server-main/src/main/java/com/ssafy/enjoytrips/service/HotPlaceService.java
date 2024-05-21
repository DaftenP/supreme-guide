package com.ssafy.enjoytrips.service;

import com.ssafy.enjoytrips.model.dto.HotPlace;
import com.ssafy.enjoytrips.model.dto.SearchCondition;

import java.util.List;

public interface HotPlaceService {
    // 전체 조회
    List<HotPlace> list(SearchCondition searchCondition);
    // 개별 조회
    HotPlace select(int hotplaceId);
    // 등록
    int regist(HotPlace hotPlace) throws Exception;
    // 수정
    int modify(HotPlace hotPlace);
    // 삭제
    int delete(int hotplaceId);

}
