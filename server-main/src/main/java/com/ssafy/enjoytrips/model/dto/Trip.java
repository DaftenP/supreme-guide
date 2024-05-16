package com.ssafy.enjoytrips.model.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Trip {
	private int tripId;
	private String tripName;
	private String userId;
	private String userName;
	private String tripContent;
	private String tripStartDate;
	private String tripEndDate;
	private String tripCreateDate;
	private List<Attraction> tripItems;
	private List<Comment> tripComments;
	private List<Hashtag> hashtags;

}
