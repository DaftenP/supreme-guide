package com.ssafy.enjoytrips.service;

import com.ssafy.enjoytrips.model.dto.Token;
import com.ssafy.enjoytrips.model.dto.User;

public interface AuthService {
    // 로그인
    User login(User user);
    // 회원가입
    int regist(User user);

    // 토큰 재발급
    Token refreshTokens(String accessToken, String refreshToken, String id);

    // id 중복확인
    int isDuplicateId(String id);

}
