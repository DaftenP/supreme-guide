package com.ssafy.enjoytrips.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrips.model.dao.TripDao;
import com.ssafy.enjoytrips.model.dto.Trip;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import com.ssafy.enjoytrips.model.dto.User;

@Service
public class TripServiceImpl implements TripService {

	private TripDao tripDao;

	public TripServiceImpl(TripDao tripDao) {
		this.tripDao = tripDao;
	}

	@Override
	public List<Trip> search(SearchCondition condition) throws SQLException {
		return tripDao.search(condition);
	}

	@Override
	@Transactional
	public Trip select(int tripId) {
		Trip trip = tripDao.select(tripId);
		trip.setTripComments(tripDao.selectComment(tripId));
		return trip;
	}

	@Override
	@Transactional
	public int regist(Trip trip) throws SQLException {
		int result = tripDao.regist(trip);
		tripDao.registItems(trip);
//		tripDao.registHashtags(trip);
		return result;
	}

	@Override
	@Transactional
	public int modify(Trip trip) {
		int result = tripDao.modify(trip);
		tripDao.deleteItems(trip.getTripId());
		tripDao.registItems(trip);
		return result;
	}


	@Override
	public int delete(int tripId) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
