package com.ssafy.enjoytrips.service;

import com.ssafy.enjoytrips.model.dao.HotplaceDao;
import com.ssafy.enjoytrips.model.dto.HotPlace;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import com.ssafy.enjoytrips.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class HotPlaceServiceImpl implements HotPlaceService{

    private final HotplaceDao hotplaceDao;

    @Override
    public List<HotPlace> list(SearchCondition searchCondition) {
        return hotplaceDao.list(searchCondition);
    }

    @Override
    @Transactional
    public HotPlace select(int hotplaceId) {
        log.debug("service는 잘 됨");
        HotPlace hotplace = hotplaceDao.select(hotplaceId);
        hotplace.setList(hotplaceDao.selectComment(hotplaceId));
        return hotplace;
    }

    @Override
    public int regist(HotPlace hotPlace) throws Exception {
        return hotplaceDao.regist(hotPlace);
    }

    @Override
    public int modify(HotPlace hotPlace) throws Exception {
        return hotplaceDao.modify(hotPlace);
    }

    @Override
    public int delete(int hotplaceId) {
        return hotplaceDao.delete(hotplaceId);
    }
}
