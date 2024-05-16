package com.ssafy.enjoytrips.controller;

import com.ssafy.enjoytrips.model.dto.Token;
import com.ssafy.enjoytrips.model.dto.User;
import com.ssafy.enjoytrips.service.AuthService;
import com.ssafy.utils.PasswordHashingUtil;
import com.ssafy.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            User loginUser = authService.login(user);
            System.out.println("hihihi"+loginUser.getId());
            if (loginUser != null) {

                String accessToken = provider.createAccesToken(loginUser.getId());
                String refreshToken = provider.createRefreshToken(loginUser.getId());
                log.debug("accessToken: ", accessToken);
                log.debug("refreshToken: ", refreshToken);

                // 발급받은 accessToken, refreshToken을 db에 저장
                authService.saveTokens(accessToken, refreshToken, loginUser.getId());

                // json으로 token을 전달
                resultMap.put("access-token", accessToken);
                resultMap.put("refresh-token", refreshToken);

            } else {
                resultMap.put("message", "Invalid credentials");
                status = HttpStatus.UNAUTHORIZED;
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

    @PostMapping("/join")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (authService.regist(user) > 0) {
            return ResponseEntity.ok("회원 가입이 완료되었습니다.");
        } else {
            return ResponseEntity.internalServerError().body("회원 가입에 실패 하였습니다.");
        }
    }
    // 토큰 재발급
    //    리프레시 토큰의 유효성 검사
    //리프레시 토큰이 유효할 경우, 해당 토큰에 포함된 사용자 ID를 추출
    //추출된 사용자 ID를 바탕으로 새로운 액세스 토큰과 리프레시 토큰 생성
    //생성된 새로운 토큰들을 클라이언트에게 반환
//    Token refreshTokens(String accessToken, String refreshToken, String id);
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Token token) {
        if (!provider.validateToken(token.getRefreshToken()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");

        try {
            String newAccessToken = provider.createAccesToken(token.getId());
            String newRefreshToken = provider.createRefreshToken(token.getId());

            return ResponseEntity.ok(new Token(newAccessToken, newRefreshToken, token.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error refreshing Token");
        }
    }



    // id 중복확인
    @GetMapping("/validate/id/{id}")
    public ResponseEntity<?> validateId(@PathVariable("id") String id) {
        int duplicate = authService.isDuplicateId(id);
        if (duplicate == 0) {
            // 중복되지 않았을 경우 "OK" 문자열 반환
            return ResponseEntity.ok().body("OK");
        } else {
            // 중복되었을 경우 빈 문자열 반환
            return ResponseEntity.ok().body("");
        }
    }
}
