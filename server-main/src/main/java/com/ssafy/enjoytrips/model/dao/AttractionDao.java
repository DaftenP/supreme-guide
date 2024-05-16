package com.ssafy.enjoytrips.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrips.model.dto.Attraction;
import com.ssafy.enjoytrips.model.dto.SearchCondition;

@Mapper
public interface AttractionDao {

	List<Attraction> search(@Param("sido") String sido, @Param("gugun") String gugun,
			@Param("category") String contentType, @Param("condition") SearchCondition condition);

	Attraction searchById(String id);

}
