package com.ssafy.enjoytrips.service;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrips.model.dao.TripCommentDao;
import com.ssafy.enjoytrips.model.dto.Comment;

@Service
public class TripCommentServiceImpl implements CommentService{
	
	private final TripCommentDao commentDao;
	
	public TripCommentServiceImpl(TripCommentDao commentDao) {
		this.commentDao = commentDao;
	}

	@Override
	public int register(Comment comment) {
		return commentDao.regist(comment);
	}

	@Override
	public int modify(Comment comment) {
		return commentDao.modify(comment);
	}

	@Override
	public int delete(int commentId) {
		return commentDao.delete(commentId);
	}

}
