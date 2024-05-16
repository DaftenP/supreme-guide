package com.ssafy.enjoytrips.model.dao;

import com.ssafy.enjoytrips.model.dto.Token;
import com.ssafy.enjoytrips.model.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthDao {
    // 로그인
    User login(User user);
    // 회원가입
	int regist(User user);

    // 토큰 재발급
    Token refreshTokens(String accessToken, String refreshToken, String id, long refreshTokenExpiredTime);

    // id 중복확인
    int isDuplicateId(String id);

    // token 저장
    void saveTokens(Token token) throws Exception;
}
