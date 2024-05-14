package com.ssafy.enjoytrips.service;

import java.util.List;

import com.ssafy.enjoytrips.model.dto.Attraction;
import com.ssafy.enjoytrips.model.dto.SearchCondition;

public interface AttractionService {
	
	// search
	List<Attraction> search(String sido, String gugun, String contentType, SearchCondition condition);

	Attraction searchById(String id);

}
