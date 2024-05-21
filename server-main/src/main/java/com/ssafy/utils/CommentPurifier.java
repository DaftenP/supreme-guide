package com.ssafy.utils;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ssafy.enjoytrips.model.dto.Comment;


@Component
public class CommentPurifier {
	
	private RestTemplate restTemplate;

	public CommentPurifier(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public ResponseEntity<String> check(Comment comment) {
		String apiUrl = "http://localhost:8000/check";
		
		HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Comment> request = new HttpEntity<>(comment, headers);
        
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);
        
        return ResponseEntity.ok(response.getBody());
	}
	
}
