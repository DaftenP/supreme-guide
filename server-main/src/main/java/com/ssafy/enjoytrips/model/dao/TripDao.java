package com.ssafy.enjoytrips.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrips.model.dto.Comment;
import com.ssafy.enjoytrips.model.dto.Hashtag;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import com.ssafy.enjoytrips.model.dto.Trip;

@Mapper
public interface TripDao {
	
	List<Trip> search(SearchCondition condition);
	
	Trip select(int tripId);
	
	List<Comment> selectComment(int tripId);
	
	List<Hashtag> selectHashtag(int tripId);

	int regist(Trip trip);
	
	int registItems(Trip trip);

	int registHashtags(Trip trip);
	
	int modify(Trip trip);
	
	int deleteItems(int tripId);
	
	int delete(String tripId);
}
