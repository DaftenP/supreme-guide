package com.ssafy.enjoytrips.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrips.model.dto.Board;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import com.ssafy.enjoytrips.service.BoardService;

@RestController
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;

	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}
	
	// 글 등록
	@PostMapping("/regist")
	public ResponseEntity<?> regist(@RequestBody Board board) {
		try {
			int result = boardService.regist(board);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 글 개별 조회
	@GetMapping("/select/{board_id}")
	public ResponseEntity<?> select(@PathVariable int board_id) {
		try {
			Board board = boardService.select(board_id);
			if (board != null ) {
				return ResponseEntity.ok(board);
			} else {
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	// 전체 조회
	@GetMapping("/list")
	public ResponseEntity<?> list(SearchCondition condition) {
		try {
			 List<Board> list = boardService.list(condition);
			 return ResponseEntity.ok(list);
		} catch(Exception e) {
			return ResponseEntity.noContent().build();
		}
	}
	
	// 글 수정
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody Board board) {
		try {
			int result = boardService.modify(board);
			return ResponseEntity.ok(result);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 글 조회수 변경
//	@PutMapping("/updateview/{board_id}")
//	public ResponseEntity<?> updateview(@PathVariable int board_id) {
//		try {
//			boardService.updateview(board_id);
//			return ResponseEntity.ok(null);
//		} catch(Exception e) {
//			return exceptionHandling(e);
//		}
// 	}
//	
	// 글 삭제
	@DeleteMapping("/delete/{board_id}")
	public ResponseEntity<?> delete(@PathVariable int board_id) {
		try {
			int result = boardService.delete(board_id);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity
				.internalServerError()
				.body("Sorry: " + e.getMessage());
	}
	
}
