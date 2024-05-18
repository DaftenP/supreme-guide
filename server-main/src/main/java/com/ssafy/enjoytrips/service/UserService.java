package com.ssafy.enjoytrips.service;

import com.ssafy.enjoytrips.model.dto.User;

public interface UserService {
//	int regist(User user);
	int modify(User user);
	int delete(String id);
	User select(String id);
}
