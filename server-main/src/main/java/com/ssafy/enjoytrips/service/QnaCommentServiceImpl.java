package com.ssafy.enjoytrips.service;

import com.ssafy.enjoytrips.model.dao.QnaCommentDao;
import com.ssafy.enjoytrips.model.dto.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QnaCommentServiceImpl implements CommentService{
    private final QnaCommentDao qnaCommentDao;

    @Override
    public int register(Comment qnaComment) {
        return qnaCommentDao.regist(qnaComment);
    }

    @Override
    public int modify(Comment qnaComment) {
        return qnaCommentDao.modify(qnaComment);
    }

    @Override
    public int delete(int qnaId) {
        return qnaCommentDao.delete(qnaId);
    }
}
