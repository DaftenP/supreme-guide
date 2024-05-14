package com.ssafy.enjoytrips.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrips.model.dao.BoardDao;
import com.ssafy.enjoytrips.model.dto.Board;
import com.ssafy.enjoytrips.model.dto.SearchCondition;

@Service
public class BoardServiceImpl implements BoardService{
	
	private final BoardDao boardDao;
	
	public BoardServiceImpl(BoardDao boardDao) {
		super();
		this.boardDao = boardDao;
	}

	@Override
	public List<Board> list(SearchCondition condition) {
		System.out.println(condition.toString());
		return boardDao.list(condition);
	}

	@Override
	public int regist(Board board) {
		return boardDao.regist(board);
	}

	@Override
	public Board select(int board_id) {
		boardDao.updateview(board_id);
		return boardDao.select(board_id);
	}

	@Override
	public int modify(Board board) {
		return boardDao.modify(board);
	}

	@Override
	public void updateview(int board_id) {
		boardDao.updateview(board_id);
		
	}

	@Override
	public int delete(int board_id) {
		return boardDao.delete(board_id);
	}

//	private static BoardServiceImpl instance = new BoardServiceImpl();
//
//	public static BoardServiceImpl getInstance() {
//		return instance;
//	}
//
//	private BoardServiceImpl() {
//	}
//
//	private BoardDaoImpl boardDao = BoardDaoImpl.getInstance();
//
//	public List<Board> list() throws SQLException {
//		return boardDao.list();
//	}
//
//	public int write(Board board) throws SQLException {
//		return boardDao.write(board);
//	}
//
//	public Board view(int board_id) throws SQLException {
//		return boardDao.view(board_id);
//	}
//
//	public int modify(Board board) throws SQLException {
//		return boardDao.modify(board);
//	}
//
//	public int delete(int board_id) throws SQLException {
//		return boardDao.delete(board_id);
//	}

}
