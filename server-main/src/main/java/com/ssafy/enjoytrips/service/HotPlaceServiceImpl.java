package com.ssafy.enjoytrips.service;

import com.ssafy.enjoytrips.model.dao.HotplaceDao;
import com.ssafy.enjoytrips.model.dto.HotPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotPlaceServiceImpl implements HotPlaceService{

    private final HotplaceDao hotplaceDao;
    @Override
    public List<HotPlace> list() {
        return hotplaceDao.list();
    }

    @Override
    public HotPlace select(int hotplaceId) {
        return hotplaceDao.select(hotplaceId);
    }

    @Override
    public int regist(HotPlace hotPlace) {
        return hotplaceDao.regist(hotPlace);
    }

    @Override
    public int modify(HotPlace hotPlace) {
        return hotplaceDao.modify(hotPlace);
    }

    @Override
    public int delete(int hotplaceId) {
        return hotplaceDao.delete(hotplaceId);
    }

    @Override
    public int updateView(int hotplaceId) {
        return hotplaceDao.updateView(hotplaceId);
    }
}
