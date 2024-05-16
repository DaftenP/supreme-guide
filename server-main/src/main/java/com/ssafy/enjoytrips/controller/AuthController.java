package com.ssafy.enjoytrips.controller;

import com.ssafy.enjoytrips.service.AuthService;
import com.ssafy.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenProvider jwtUtil;




}
