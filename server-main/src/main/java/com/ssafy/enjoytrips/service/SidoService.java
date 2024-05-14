package com.ssafy.enjoytrips.service;

import java.util.List;

import com.ssafy.enjoytrips.model.dto.Gugun;
import com.ssafy.enjoytrips.model.dto.Sido;

public interface SidoService {
	
	List<Sido> sidoList();

	List<Gugun> gugunList(int sidoCode);
}
