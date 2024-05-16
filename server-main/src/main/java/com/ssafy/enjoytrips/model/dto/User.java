package com.ssafy.enjoytrips.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	private String id, pw, name, email, salt, createDate;
	private int admin;

	public User() {
	}

	public User(String id, String pw, String name, String email, String salt, int admin, String createDate) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.email = email;
		this.salt = salt;
		this.admin = admin;
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", pw='" + pw + '\'' +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", salt='" + salt + '\'' +
				", createDate='" + createDate + '\'' +
				", admin=" + admin +
				'}';
	}
}
