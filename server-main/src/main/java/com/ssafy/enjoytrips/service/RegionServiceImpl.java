package com.ssafy.enjoytrips.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrips.model.dao.RegionDao;
import com.ssafy.enjoytrips.model.dto.Region;

@Service
public class RegionServiceImpl implements RegionService {

	private final RegionDao regionDao;

	public RegionServiceImpl(RegionDao regionDao) {
		super();
		this.regionDao = regionDao;
	}

	@Override
	public List<Region> getRegion() {
		return regionDao.getRegion();
	}



}
