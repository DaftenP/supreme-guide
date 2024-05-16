package com.ssafy.enjoytrips.service;

import com.ssafy.enjoytrips.model.dao.QnaCommentDao;
import com.ssafy.enjoytrips.model.dto.QnaComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final QnaCommentDao qnaCommentDao;

    @Override
    public int register(QnaComment qnaComment) {
        return qnaCommentDao.regist(qnaComment);
    }

    @Override
    public List<QnaComment> list(int qnaId) {
        return qnaCommentDao.list(qnaId);
    }

    @Override
    public int modify(QnaComment qnaComment) {
        return qnaCommentDao.modify(qnaComment);
    }

    @Override
    public int delete(int qnaId) {
        return qnaCommentDao.delete(qnaId);
    }
}
