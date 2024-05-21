package com.ssafy.enjoytrips.service;

import com.ssafy.enjoytrips.model.dao.HotplaceDao;
import com.ssafy.enjoytrips.model.dto.HotPlace;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import com.ssafy.enjoytrips.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotPlaceServiceImpl implements HotPlaceService{

    private final HotplaceDao hotplaceDao;
    private final FileUtil fileUtil;

    @Override
    public List<HotPlace> list(SearchCondition searchCondition) {
        return hotplaceDao.list(searchCondition);
    }

    @Override
    public HotPlace select(int hotplaceId) {
        return hotplaceDao.select(hotplaceId);
    }

    @Override
    public int regist(HotPlace hotPlace) throws Exception {
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
}
