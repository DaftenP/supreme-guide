package com.ssafy.enjoytrips.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrips.model.dto.SearchCondition;
import com.ssafy.enjoytrips.model.dto.Trip;
import com.ssafy.enjoytrips.service.TripService;



@RestController
@RequestMapping("/trip")
public class TripController {


	private TripService tripService;

	public TripController(TripService tripService) {
		this.tripService = tripService;
	}

	@GetMapping("")
	public ResponseEntity<?> getList(SearchCondition condition) {
		try {
			List<Trip> list = tripService.search(condition);
			return ResponseEntity.ok(list) ;
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getTrip(@PathVariable int id) {
		try {
			Trip trip = tripService.select(id);
			return ResponseEntity.ok(trip);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	

	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.internalServerError().body("Sorry: " + e.getMessage());
	}

}
