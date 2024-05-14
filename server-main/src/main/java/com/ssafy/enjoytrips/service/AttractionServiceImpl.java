package com.ssafy.enjoytrips.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrips.model.dao.AttractionDao;
import com.ssafy.enjoytrips.model.dto.Attraction;
import com.ssafy.enjoytrips.model.dto.SearchCondition;

@Service
public class AttractionServiceImpl implements AttractionService {

	private final AttractionDao attractionDao;

	public AttractionServiceImpl(AttractionDao attractionDao) {
		super();
		this.attractionDao = attractionDao;
	}

	@Override
	public List<Attraction> search(String sido, String gugun, String contentType, SearchCondition condition) {
		return attractionDao.search(sido, gugun, contentType, condition);
	}

}
