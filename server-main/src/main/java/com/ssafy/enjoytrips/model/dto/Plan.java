package com.ssafy.enjoytrips.model.dto;


import java.util.List;

public class Plan {
	private int tripPlanId;
	private String tripPlanName;
	private String tripPlanUserId;
	private String tripPlanContent;
	private String startDate;
	private String endDate;
	private String createdDate;
	private List<Attraction> tripPlanItems;
	
	
	public Plan() {
		super();
	}

	public int getTripPlanId() {
		return tripPlanId;
	}

	public void setTripPlanId(int tripPlanId) {
		this.tripPlanId = tripPlanId;
	}

	public String getTripPlanName() {
		return tripPlanName;
	}

	public void setTripPlanName(String tripPlanName) {
		this.tripPlanName = tripPlanName;
	}

	public String getTripPlanUserId() {
		return tripPlanUserId;
	}

	public void setTripPlanUserId(String tripPlanUserId) {
		this.tripPlanUserId = tripPlanUserId;
	}

	public String getTripPlanContent() {
		return tripPlanContent;
	}

	public void setTripPlanContent(String tripPlanContent) {
		this.tripPlanContent = tripPlanContent;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public List<Attraction> getTripPlanItems() {
		return tripPlanItems;
	}

	public void setTripPlanItems(List<Attraction> tripPlanItems) {
		this.tripPlanItems = tripPlanItems;
	}

	public Plan(int tripPlanId, String tripPlanName, String tripPlanUserId, String tripPlanContent, String startDate,
			String endDate, String createdDate, List<Attraction> tripPlanItems) {
		super();
		this.tripPlanId = tripPlanId;
		this.tripPlanName = tripPlanName;
		this.tripPlanUserId = tripPlanUserId;
		this.tripPlanContent = tripPlanContent;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createdDate = createdDate;
		this.tripPlanItems = tripPlanItems;
	}


}
