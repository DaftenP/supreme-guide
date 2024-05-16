package com.ssafy.enjoytrips.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrips.model.dto.Region;

@Mapper
public interface RegionDao {
	
	List<Region> getRegion();
}
