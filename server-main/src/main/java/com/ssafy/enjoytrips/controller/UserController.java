package com.ssafy.enjoytrips.controller;

import com.ssafy.utils.TokenProvider;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.enjoytrips.model.dto.User;
import com.ssafy.enjoytrips.service.UserService;

import jakarta.servlet.http.HttpServlet;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Log4j2
public class UserController extends HttpServlet {

	private final UserService userService;
	private final TokenProvider tokenProvider;


	@GetMapping("/search")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User information retrieved"),
			@ApiResponse(responseCode = "204", description = "User not found"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	public ResponseEntity<?> select(@RequestHeader("Authorization") String authorizationHeader) {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			// 헤더가 없거나 Bearer 토큰이 아닌 경우의 처리
			// 예: 401 Unauthorized 반환
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
		}

		// "Bearer " 이후의 토큰 추출
		String token = authorizationHeader.substring(7);
		System.out.println("token---------" + token);

		// 추출한 accessToken을 이용하여 userId 조회
		String userId = tokenProvider.getUserId(token);

		System.out.println(userId);
		// userId를 이용하여 유저 정보 조회
		User user = userService.select(userId);

		// 유저 정보가 있을 경우 200 OK 반환, 없을 경우 204 No Content 반환
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.noContent().build();
	}

	@PutMapping("/modify")
	public ResponseEntity<?> modify(@RequestBody User user) {
		System.out.println(user.toString());
		if (userService.modify(user) > 0) {
			return ResponseEntity.ok("회원 정보 수정이 완료되었습니다.");
		} else {
			return ResponseEntity.internalServerError().body("회원 정보 수정에 실패 하였습니다.");
		}
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody User user) {
		System.out.println(user.toString());
		if (userService.delete(user) > 0) {
			return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
		} else {
			return ResponseEntity.internalServerError().body("회원 탈퇴에 실패 하였습니다.");
		}
	}
	

}
