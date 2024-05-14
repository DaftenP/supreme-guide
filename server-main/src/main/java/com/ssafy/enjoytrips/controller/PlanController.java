package com.ssafy.enjoytrips.controller;

import java.sql.SQLException;
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

import com.ssafy.enjoytrips.model.dto.Plan;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import com.ssafy.enjoytrips.service.PlanService;



@RestController
@RequestMapping("/plan")
public class PlanController {


	private PlanService planService;

	public PlanController(PlanService planService) {
		this.planService = planService;
	}

	@GetMapping("/list")
	public ResponseEntity<?> getList(SearchCondition condition) throws SQLException {
		List<Plan> list = planService.search(condition);
		if(list.size() > 0) {
			return ResponseEntity.ok(list) ;
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@GetMapping("/view/{id}")
	public ResponseEntity<?> view(@PathVariable String id) throws SQLException {
		Plan plan = planService.select(id);
		if (plan != null) {
			return ResponseEntity.ok(plan) ;
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@PostMapping("")
	public ResponseEntity<?> doRegist(@RequestBody Plan plan) throws SQLException {
		if (planService.regist(plan) > 0) {
			return ResponseEntity.ok("여행계획 등록이 완료되었습니다.");
		} else {
			return ResponseEntity.internalServerError().body("여행계획 등록에 실패 하였습니다.");
		}
	}
	
	@PutMapping("")
	public ResponseEntity<?> modify(@RequestBody Plan plan) {
		if (planService.modify(plan) > 0) {
			return ResponseEntity.ok("여행계획 수정이 완료되었습니다.");
		} else {
			return ResponseEntity.internalServerError().body("여행계획 수정에 실패 하였습니다.");
		}
	}
	
	@DeleteMapping("/view/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) throws SQLException {
		if (planService.delete(id) > 0) {
			return ResponseEntity.ok("여행계획 삭제가 완료되었습니다.");
		} else {
			return ResponseEntity.internalServerError().body("여행계획 삭제에 실패 하였습니다.");
		}
	}

}
