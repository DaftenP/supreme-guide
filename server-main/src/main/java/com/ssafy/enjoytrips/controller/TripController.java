package com.ssafy.enjoytrips.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrips.model.dto.Comment;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import com.ssafy.enjoytrips.model.dto.Trip;
import com.ssafy.enjoytrips.service.CommentService;
import com.ssafy.enjoytrips.service.TripService;
import com.ssafy.utils.CommentPurifier;
import com.ssafy.utils.TokenProvider;

@RestController
@RequestMapping("/trip")
public class TripController {

	private final TripService tripService;
	private final CommentService tripCommentService;
	private final TokenProvider tokenProvider;
	private final CommentPurifier commentPurifier;

	public TripController(TripService tripService,
			@Qualifier(value = "tripCommentServiceImpl") CommentService tripCommentService, TokenProvider tokenProvider,
			CommentPurifier commentPurifier) {
		this.tripService = tripService;
		this.tripCommentService = tripCommentService;
		this.tokenProvider = tokenProvider;
		this.commentPurifier = commentPurifier;
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
	public ResponseEntity<?> registTrip(@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody Trip trip) {
		try {
			if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
			}

			String token = authorizationHeader.substring(7);
			String userId = tokenProvider.getUserId(token);

			if (userId == null) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
			}
			trip.setUserId(userId);
			return ResponseEntity.ok(tripService.regist(trip));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 여행계획 수정
	@PutMapping("/modify")
	public ResponseEntity<?> modify(@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody Trip trip) {
		try {
			if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
			}

			String token = authorizationHeader.substring(7);
			String userId = tokenProvider.getUserId(token);

			if (userId == null) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
			}
			trip.setUserId(userId);
			return ResponseEntity.ok(tripService.modify(trip));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 여행계획 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@RequestHeader("Authorization") String authorizationHeader, @PathVariable int id) {
		try {
			if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
			}

			String token = authorizationHeader.substring(7);
			String userId = tokenProvider.getUserId(token);

			if (userId == null) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
			}
			return ResponseEntity.ok(tripService.delete(id));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// --- 댓글 ---

	// 댓글 등록
	@PostMapping("/comment/regist")
	public ResponseEntity<?> registComment(@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody Comment comment) {
		try {
			if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
			}

			String token = authorizationHeader.substring(7);
			String userId = tokenProvider.getUserId(token);

			if (userId == null) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
			}
			comment.setUserId(userId);
			try {
				ResponseEntity<String> check = commentPurifier.check(comment);
				if (!"null".equals(check.getBody())) {
					comment.setHarmful(true);
				}
			} catch (Exception e) {
				System.out.println("API 서버 통신 오류!!");
			}
			return ResponseEntity.ok(tripCommentService.register(comment));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 댓글 수정
	@PutMapping("/comment/modify")
	public ResponseEntity<?> modifyComment(@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody Comment comment) {
		try {
			if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
			}

			String token = authorizationHeader.substring(7);
			String userId = tokenProvider.getUserId(token);

			if (userId == null) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
			}
			comment.setUserId(userId);
			try {
				ResponseEntity<String> check = commentPurifier.check(comment);
				if (!"null".equals(check.getBody())) {
					comment.setHarmful(true);
				}
			} catch (Exception e) {
				System.out.println("API 서버 통신 오류!!");
			}
			return ResponseEntity.ok(tripCommentService.modify(comment));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// 댓글 삭제
	@DeleteMapping("/comment/{id}")
	public ResponseEntity<?> deleteComment(@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable int id) {
		try {
			if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
			}

			String token = authorizationHeader.substring(7);
			String userId = tokenProvider.getUserId(token);

			if (userId == null) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
			}
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
