package com.ssafy.enjoytrips.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.enjoytrips.model.dto.Trip;
import com.ssafy.enjoytrips.model.dto.SearchCondition;

public interface TripService {

	List<Trip> search(SearchCondition condition) throws SQLException;

	Trip select(int tripId);

	int regist(Trip trip) throws SQLException;
	
	int modify(Trip trip);
	
	int delete(int tripId);
}
