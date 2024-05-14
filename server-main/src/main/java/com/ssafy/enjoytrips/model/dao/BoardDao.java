package com.ssafy.enjoytrips.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrips.model.dto.Board;
import com.ssafy.enjoytrips.model.dto.SearchCondition;

@Mapper
public interface BoardDao {
	// 글 전체 조회
	List<Board> list(SearchCondition condition);
	// 글 등록
	int regist(Board board);
	// 글 개별 조회
	Board select(int board_id);
	// 글 수정
	int modify(Board board);
	// 글 조회수 변경
	void updateview(int board_id);
	// 글 삭제
	int delete(int board_id);
}
