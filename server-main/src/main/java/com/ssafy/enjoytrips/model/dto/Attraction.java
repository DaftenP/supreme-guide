package com.ssafy.enjoytrips.model.dto;

public class Attraction {
	private String title, addr1, addr2, zipCode, firstImage;
	private int category, sidoCode, gugunCode, id;
	private double mapx, mapy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Attraction() {
	}

	public String getFirstimage() {
		return firstImage;
	}

	public void setFirstimage(String firstimage) {
		this.firstImage = firstimage;
	}



	public Attraction(String title, String addr1, String addr2, String zipcode, String firstimage, int category,
			int sidocode, int guguncode, int id, double mapx, double mapy) {
		super();
		this.title = title;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.zipCode = zipcode;
		this.firstImage = firstimage;
		this.category = category;
		this.sidoCode = sidocode;
		this.gugunCode = guguncode;
		this.id = id;
		this.mapx = mapx;
		this.mapy = mapy;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getZipcode() {
		return zipCode;
	}

	public void setZipcode(String zipcode) {
		this.zipCode = zipcode;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getSidocode() {
		return sidoCode;
	}

	public void setSidocode(int sidocode) {
		this.sidoCode = sidocode;
	}

	public int getGuguncode() {
		return gugunCode;
	}

	public void setGuguncode(int guguncode) {
		this.gugunCode = guguncode;
	}

	public double getMapx() {
		return mapx;
	}

	public void setMapx(double mapx) {
		this.mapx = mapx;
	}

	public double getMapy() {
		return mapy;
	}

	public void setMapy(double mapy) {
		this.mapy = mapy;
	}
}
