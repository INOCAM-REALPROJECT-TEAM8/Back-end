package com.example.backend.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.user.dto.TokenDto;
import com.example.backend.user.dto.UserResponseDto;
import com.example.backend.user.service.GoogleService;
import com.example.backend.util.ImageUtil;
import com.example.backend.util.JwtUtil;
import com.example.backend.util.StatusResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GoogleController {
	private static final Logger logger = LoggerFactory.getLogger(GoogleController.class);
	private final GoogleService googleService;
	private final ImageUtil imageUtil;

	// 로그인 페이지 url 얻기
	@GetMapping("/login/oauth2/google")
	public ResponseEntity<StatusResponseDto> getLoginUrl() {
		log.info("구글 로그인 페이지 불러오기");
		String url = googleService.getGoogleLoginForm();
		return new ResponseEntity<>(new StatusResponseDto(url, true), HttpStatus.OK);
	}

	// 구글 로그인
	@GetMapping("/api/users/oauth2/google")
	public ResponseEntity<UserResponseDto> googleLogin(@RequestParam String code, HttpServletResponse response) throws
		JsonProcessingException {
		log.info("구글 로그인 요청");
		TokenDto tokenDto = googleService.googleLogin(code);
		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, tokenDto.getAccessToken());
		response.addHeader(JwtUtil.REFRESH_HEADER, tokenDto.getRefreshToken());
		return new ResponseEntity<>(
			new UserResponseDto("구글 로그인이 완료되었습니다.", true, imageUtil.getImageUrlFromUser(tokenDto.getUser())),
			HttpStatus.OK);
	}
}
