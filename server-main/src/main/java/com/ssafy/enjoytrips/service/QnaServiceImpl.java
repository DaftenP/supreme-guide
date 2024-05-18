package com.ssafy.enjoytrips.service;

import com.ssafy.enjoytrips.model.dao.QnaDao;
import com.ssafy.enjoytrips.model.dto.Comment;
import com.ssafy.enjoytrips.model.dto.Qna;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaServiceImpl implements QnaService{
    private final QnaDao qnaDao;

    @Override
    public List<Qna> list(SearchCondition searchCondition) {
        return qnaDao.list(searchCondition);
    }
    @Override
    public Qna select(int qnaId) {

        Qna qna = qnaDao.select(qnaId);
        List<Comment> comments = qnaDao.selectComment(qnaId);
        qna.setList(comments);
        return qna;
    }

    @Override
    public int regist(Qna qna) {
        return qnaDao.regist(qna);
    }

    @Override
    public int modify(Qna qna) {
        return qnaDao.modify(qna);
    }

    @Override
    public int delete(int qnaId) {
        return qnaDao.delete(qnaId);
    }

    @Override
    public void updateView(int qnaId) {
        qnaDao.updateView(qnaId);
    }
}
