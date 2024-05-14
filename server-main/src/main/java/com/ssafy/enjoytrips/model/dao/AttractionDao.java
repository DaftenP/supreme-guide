package com.ssafy.enjoytrips.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrips.model.dto.Attraction;
import com.ssafy.enjoytrips.model.dto.SearchCondition;

@Mapper
public interface AttractionDao {

	// search
	List<Attraction> search(String sido, String gugun, String contentType, SearchCondition condition);

}
