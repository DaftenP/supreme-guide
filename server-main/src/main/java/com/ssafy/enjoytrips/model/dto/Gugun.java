package com.ssafy.enjoytrips.model.dto;

public class Gugun {

	private int code, sidoCode;
	private String name;

	public Gugun() {
	}

	public Gugun(int code, int sidoCode, String name) {
		super();
		this.code = code;
		this.sidoCode = sidoCode;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getSidoCode() {
		return sidoCode;
	}

	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
