package com.ssafy.enjoytrips.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrips.model.dto.User;

@Mapper
public interface UserDao {

	User login(User user);
	int regist(User user);
	int modify(User user);
	int delete(User user);
	User select(String id);
}
