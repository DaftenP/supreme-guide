package com.ssafy.enjoytrips.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrips.model.dto.User;
import com.ssafy.enjoytrips.service.UserService;

import jakarta.servlet.http.HttpServlet;

@RestController
@RequestMapping("/user")
public class UserController extends HttpServlet {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}


	@GetMapping("/search/{id}")
	public ResponseEntity<?> select(@PathVariable String id) {
		User user = userService.select(id);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		User loginUser = userService.login(user);
		if (loginUser != null) {
			return ResponseEntity.ok(loginUser);
		} else {
			return ResponseEntity.noContent().build();
		}
	}


	@PostMapping("/regist")
	public ResponseEntity<?> regist(@RequestBody User user) {
		if (userService.regist(user) > 0) {
			return ResponseEntity.ok("회원 가입이 완료되었습니다.");
		} else {
			return ResponseEntity.internalServerError().body("회원 가입에 실패 하였습니다.");
		}
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
