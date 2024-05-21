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
		commentDao.regist(comment);
		return comment.getId(); 
	}

	@Override
	public int modify(Comment comment) {
		return commentDao.modify(comment);
	}

	@Override
	public int delete(int commentId) {
		return commentDao.delete(commentId);
	}

	// 추후 추가 -> 댓글 작성자 조회 -> 수정과 삭제 권한 확인하기 위함
	@Override
	public Comment selectCommentWriter(int qnaCommentId) {
		return null;
	}

}
