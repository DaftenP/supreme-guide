package com.ssafy.enjoytrips.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.enjoytrips.model.dto.Plan;
import com.ssafy.enjoytrips.model.dto.SearchCondition;

public interface PlanService {

	List<Plan> search(SearchCondition condition) throws SQLException;

	Plan select(String planId);

	int regist(Plan plan) throws SQLException;
	
	int modify(Plan plan);
	
	int delete(String planId);
}
