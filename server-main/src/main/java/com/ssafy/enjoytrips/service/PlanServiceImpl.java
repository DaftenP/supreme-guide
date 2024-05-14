package com.ssafy.enjoytrips.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrips.model.dao.PlanDao;
import com.ssafy.enjoytrips.model.dto.Plan;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import com.ssafy.enjoytrips.model.dto.User;

@Service
public class PlanServiceImpl implements PlanService {

	private PlanDao planDao;

	public PlanServiceImpl(PlanDao planDao) {
		this.planDao = planDao;
	}

	@Override
	@Transactional
	public int regist(Plan plan) throws SQLException {
		int cnt = planDao.registPlan(plan);
		if (cnt > 0) {
			return planDao.registPlanItems(plan);
		}
		return 0;
	}

	@Override
	public List<Plan> search(SearchCondition condition) throws SQLException {
		return planDao.search(condition);
	}

	@Override
	public Plan select(String planId) {
		return planDao.select(planId);
	}

	@Override
	@Transactional
	public int modify(Plan plan) {
		planDao.deletePlanItems(plan.getTripPlanId());
		planDao.modify(plan);
		return planDao.registPlanItems(plan);
	}

	@Override
	public int delete(String planId) {
		return planDao.delete(planId);
	}

}
