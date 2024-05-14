package com.ssafy.enjoytrips.service;

import com.ssafy.enjoytrips.model.dto.User;

public interface UserService {
	User login(User user);
	int regist(User user);
	int modify(User user);
	int delete(User user);
	User select(String id);
}
