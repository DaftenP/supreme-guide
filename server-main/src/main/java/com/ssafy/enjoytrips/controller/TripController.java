package com.ssafy.enjoytrips.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrips.model.dto.Comment;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import com.ssafy.enjoytrips.model.dto.Trip;
import com.ssafy.enjoytrips.service.CommentService;
import com.ssafy.enjoytrips.service.TripService;

@RestController
@RequestMapping("/trip")
public class TripController {

	private TripService tripService;
	private CommentService tripCommentService;

	public TripController(TripService tripService,
			@Qualifier(value = "tripCommentServiceImpl") CommentService tripCommentService) {
		this.tripService = tripService;
		this.tripCommentService = tripCommentService;
	}

	// 여행계획 전체 조회
	@GetMapping("")
	public ResponseEntity<?> getList(SearchCondition condition) {
		try {
			List<Trip> list = tripService.search(condition);
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 여행계획 상세 조회
	@GetMapping("/{id}")
	public ResponseEntity<?> getTrip(@PathVariable int id) {
		try {
			Trip trip = tripService.select(id);
			return ResponseEntity.ok(trip);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 여행계획 등록
	@PostMapping("/regist")
	public ResponseEntity<?> registTrip(@RequestBody Trip trip) {
		try {
			return ResponseEntity.ok(tripService.regist(trip));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 여행계획 수정
	@PutMapping("/modify")
	public ResponseEntity<?> modify(@RequestBody Trip trip) {
		try {
			return ResponseEntity.ok(tripService.modify(trip));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 여행계획 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		try {
			return ResponseEntity.ok(tripService.delete(id));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// --- 댓글 ---
	
	// 댓글 등록
	@PostMapping("/comment/regist")
	public ResponseEntity<?> registComment(@RequestBody Comment comment) {
		try {
			return ResponseEntity.ok(tripCommentService.register(comment));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 댓글 수정
	@PutMapping("/comment/modify")
	public ResponseEntity<?> modifyComment(@RequestBody Comment comment) {
		try {
			return ResponseEntity.ok(tripCommentService.modify(comment));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}		
	
	// 댓글 삭제
	@DeleteMapping("/comment/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable int id) {
		try {
			return ResponseEntity.ok(tripCommentService.delete(id));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.internalServerError().body("Sorry: " + e.getMessage());
	}

}
