package com.ssafy.enjoytrips.controller;

import com.ssafy.enjoytrips.model.dto.Token;
import com.ssafy.enjoytrips.model.dto.User;
import com.ssafy.enjoytrips.service.AuthService;
import com.ssafy.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Log4j2
public class AuthController {

    private final AuthService authService;
    private final TokenProvider provider;

    // 로그인
//    Token login(User user);
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            User loginUser = authService.login(user);
            if (loginUser != null) {
                String accessToken = provider.createAccesToken(loginUser.getId());
                String refreshToken = provider.createRefreshToken(loginUser.getId());
                log.debug("accessToken: ", accessToken);
                log.debug("refreshToken: ", refreshToken);

                // 발급받은 token들 db에 저장


                // json으로 token을 전달
                resultMap.put("access-token", accessToken);
                resultMap.put("refresh-token", refreshToken);

            }

        } catch (Exception e) {
            log.debug("로그인 에러 발생 ", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
    // 회원가입
//    int regist(User user);

    // 토큰 재발급
//    Token refreshTokens(String accessToken, String refreshToken, String id);

    // id 중복확인
//    int isDuplicateId(String id);







}
