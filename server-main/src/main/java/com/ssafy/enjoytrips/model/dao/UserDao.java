package com.ssafy.enjoytrips.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrips.model.dto.User;

@Mapper
public interface UserDao {
	// 정보 수정
	int modify(User user);
	// 회원 탈퇴
	int delete(String id);
	// 정보 조회
	User select(String id);

}
