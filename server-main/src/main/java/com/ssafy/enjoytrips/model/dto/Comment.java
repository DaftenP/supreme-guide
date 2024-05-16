package com.ssafy.enjoytrips.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	private int id;
	private boolean harmful;	// tinyint와 대응
	private String content;
	private String userId;
	private String userName;	//user table 과 조인해서 가져올 것
	private int articleId;	// 연결된 게시글
	
}
