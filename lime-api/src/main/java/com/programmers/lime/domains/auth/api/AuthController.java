package com.programmers.lime.domains.auth.api;

import static com.programmers.lime.domains.member.api.MemberController.*;
import static org.springframework.http.HttpHeaders.*;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.lime.domains.auth.application.OAuthUserService;
import com.programmers.lime.domains.member.api.dto.response.MemberLoginResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {

	private final OAuthUserService oauthUserService;

	@GetMapping("/auth/kakao/callback")
	public ResponseEntity<MemberLoginResponse> loginKakao(
		@RequestParam final String code,
		HttpServletResponse response
	) {
		MemberLoginResponse loginResponse = oauthUserService.login(code);
		sendRefreshToken(response, loginResponse);

		return ResponseEntity.ok(loginResponse);
	}

	@GetMapping("/join")
	public ResponseEntity<String> join(){
		return ResponseEntity.ok("join");
	}

	private void sendRefreshToken(
		final HttpServletResponse response,
		final MemberLoginResponse loginResponse
	) {
		final ResponseCookie cookie = ResponseCookie.from("refresh-token", loginResponse.refreshToken())
			.maxAge(COOKIE_AGE_SECONDS)
			.secure(true)
			.httpOnly(true)
			.sameSite("None")
			.path("/")
			.build();

		response.addHeader(SET_COOKIE, String.valueOf(cookie));
	}
}
