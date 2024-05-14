package com.ssafy.enjoytrips.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrips.model.dto.Attraction;
import com.ssafy.enjoytrips.model.dto.SearchCondition;
import com.ssafy.enjoytrips.service.AttractionService;
import com.ssafy.enjoytrips.service.SidoService;

@RestController
@RequestMapping("/attraction")
public class AttractionController {

	private final SidoService sidoService;
	private final AttractionService attractionInfoService;
	
	public AttractionController(SidoService sidoService, AttractionService attractionInfoService) {
		super();
		this.sidoService = sidoService;
		this.attractionInfoService = attractionInfoService;
	}



	@GetMapping("/search")
	public ResponseEntity<?> search(
			@RequestParam(required = false) String sido,
			@RequestParam(required = false) String gugun,
			@RequestParam(required = false) String category,
			SearchCondition condition) {
		try {
			List<Attraction> list = attractionInfoService.search(sido, gugun, category, condition);
			return ResponseEntity.ok(list);
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
