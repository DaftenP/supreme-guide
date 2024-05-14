package com.ssafy.enjoytrips.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrips.model.dto.Gugun;
import com.ssafy.enjoytrips.model.dto.Sido;

@Mapper
public interface SidoDao {
	
	// sido search
	List<Sido> sidoList();
	
	// gugun search
	List<Gugun> gugunList(int sidoCode);
}
