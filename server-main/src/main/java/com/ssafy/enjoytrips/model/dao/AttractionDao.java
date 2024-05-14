package com.ssafy.enjoytrips.model.dao;

import java.util.List;
import com.ssafy.enjoytrips.model.dto.Gugun;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import com.ssafy.enjoytrips.model.dto.Sido;
import com.ssafy.enjoytrips.model.dto.Attraction;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttractionDao {

//	// gugun search
//	List<Gugun> gugunList();

	// search
	List<Attraction> search(String sido, String gugun, String contentType, SearchCondition condition);

}
