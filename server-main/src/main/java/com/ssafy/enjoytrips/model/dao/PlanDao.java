package com.ssafy.enjoytrips.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrips.model.dto.Plan;
import com.ssafy.enjoytrips.model.dto.SearchCondition;

@Mapper
public interface PlanDao {
	
	List<Plan> search(SearchCondition condition);
	
	Plan select(String planId);

	int registPlan(Plan plan);
	
	int registPlanItems(Plan plan);
	
	int modify(Plan plan);
	
	int deletePlanItems(int i);
	
	int delete(String planId);
}
