package com.ssafy.utils;

import com.ssafy.enjoytrips.exception.UnAuthorizedException;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class TokenProvider {

    @Value("${jwt.salt}")
    private String salt;

    @Value("${jwt.access-token.expiretime}")
    private long accessTokenExpireTime;

    @Value("${jwt.refresh-token.expiretime}")
    private long refreshTokenExpireTime;

    public String createAccesToken(String userId) {
        return create(userId, "access-token", accessTokenExpireTime);
    }

    public String createRefreshToken(String userId) {
        long refreshTokenExpiredTime = System.currentTimeMillis() + refreshTokenExpireTime;
        return create(userId, "refresh-token", refreshTokenExpiredTime);
    }

    // JWT 토큰 생성 및 발급
    //		key : Claim에 셋팅될 key 값
    //	value : Claim에 셋팅 될 data 값
    //	subject : payload에 sub의 value로 들어갈 subject값
    //	expire : 토큰 유효기간 설정을 위한 값
    //	jwt 토큰의 구성 : header + payload + signature
    private String create(String userId, String subject, long expireTime) {
        // Payload 설정: 생성일, 유효기간, 토큰 유형, 데이터 등 정보를 세팅
        Claims claims = Jwts.claims()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime));

        claims.put("userId", userId);

        String jwt = Jwts.builder()
                // Header 설정
                .setHeaderParam("typ", "JWT").setClaims(claims)
                // Signature
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();

        return jwt;
    }

    // Signature 설정에 들어갈 key 생성
    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = salt.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            if (log.isInfoEnabled()) {
                e.printStackTrace();
            } else {
                log.error("Making JWT Key Error : " + e.getMessage());
            }
        }
        return key;
    }
    // JWT 토큰의 유효성 검사
    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parser()
                    .setSigningKey(this.generateKey())
                    .parseClaimsJws(jwtToken);

            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }



    // JWT 토큰에서 정보 추출 -> userId
    public String getUserId(String authorization) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(authorization);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UnAuthorizedException();
        }
        Map<String, Object> value = claims.getBody();
        log.info("value : ", value);
        return (String) value.get("userId");
    }

}
