package com.ssafy.enjoytrips.service;

import com.ssafy.enjoytrips.model.dao.HotPlaceCommentDao;
import com.ssafy.enjoytrips.model.dto.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotPlaceCommentServiceImpl implements CommentService {

    private final HotPlaceCommentDao hotPlaceCommentDao;


    @Override
    public int register(Comment comment) {
        return hotPlaceCommentDao.regist(comment);
    }

    // 댓글수정 구현 x
    @Override
    public int modify(Comment comment) {
        return hotPlaceCommentDao.modify(comment);
    }

    @Override
    public int delete(int id) {
        return hotPlaceCommentDao.delete(id);
    }

    @Override
    public Comment selectCommentWriter(int id) {
        return hotPlaceCommentDao.selectCommentWriter(id);
    }
}
