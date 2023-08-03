package com.example.backend.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.user.service.GoogleService;
import com.example.backend.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GoogleController {
	private final GoogleService googleService;
	// 로그인 페이지 url 얻기
	@GetMapping("/login/oauth2/google")
	public String getLoginUrl() {
		return googleService.getGoogleLoginForm();
	}

	// 구글 로그인
	@GetMapping("/api/users/oauth2/google")
	public void kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
		String token = googleService.googleLogin(code);
		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
	}
}