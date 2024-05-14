package com.ssafy.enjoytrips.model.dto;

public class User {
	private String id, pw, name, email, salt;
	private boolean admin;

	public User() {
	}

	public User(String id, String pw, String name, String email, String salt, boolean admin) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.email = email;
		this.salt = salt;
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", pw=" + pw + ", name=" + name + ", email=" + email + ", salt=" + salt + ", admin="
				+ admin + "]";
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
