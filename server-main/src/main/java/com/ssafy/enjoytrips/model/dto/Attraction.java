package com.ssafy.enjoytrips.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attraction {
	private String title, addr1, addr2, zipcode, firstImage, tel;
	private int category, sidoCode, gugunCode, contentId;
	private double latitude, longitude;

}
