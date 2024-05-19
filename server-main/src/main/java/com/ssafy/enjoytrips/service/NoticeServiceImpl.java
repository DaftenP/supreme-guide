package com.ssafy.enjoytrips.service;

import com.ssafy.enjoytrips.model.dao.NoticeDao;
import com.ssafy.enjoytrips.model.dto.Notice;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeDao noticeDao;
    @Override
    public List<Notice> list(SearchCondition searchCondition) {

        return noticeDao.list(searchCondition);
    }

    @Override
    public Notice select(int noticeId) {
        noticeDao.updateView(noticeId);
        return noticeDao.select(noticeId);
    }

    @Override
    public int regist(Notice notice) {
        return noticeDao.regist(notice);
    }

    @Override
    public int modify(Notice notice) {
        return noticeDao.modify(notice);
    }

    @Override
    public int delete(int noticeId) {
        return noticeDao.delete(noticeId);
    }

    @Override
    public int updateView(int noticeId) {
        return noticeDao.updateView(noticeId);
    }
}
