package com.ssafy.enjoytrips.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Region {
	private int sidoCode;
	private String sidoName;
	private List<Gugun> gugunList;

}
