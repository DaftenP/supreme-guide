package com.ssafy.enjoytrips.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrips.model.dao.SidoDao;
import com.ssafy.enjoytrips.model.dto.Gugun;
import com.ssafy.enjoytrips.model.dto.Sido;

@Service
public class SidoServiceImpl implements SidoService{

	private final SidoDao sidoDao;
	
	
	public SidoServiceImpl(SidoDao sidoDao) {
		super();
		this.sidoDao = sidoDao;
	}


	@Override
	public List<Sido> sidoList() {
		return sidoDao.sidoList();
	}


	@Override
	public List<Gugun> gugunList(int sidoCode) {
		return sidoDao.gugunList(sidoCode);
	}

//	private static SidoServiceImpl instance = new SidoServiceImpl();
//
//	public static SidoServiceImpl getInstance() {
//		return instance ;
//	}
//
//	private SidoServiceImpl() {}
//
//	SidoDaoImpl sidoDao = SidoDaoImpl.getInstance();
//	
//	public List<Sido> getSidoList() {
//		return sidoDao.getSidoList();
//	}
//
//	public Map<Integer, List<Gugun>> getGugunMap() {
//		return sidoDao.getGugunMap();
//	}
}
