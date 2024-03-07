package com.programmers.lime.domains.auth.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.lime.domains.auth.application.OAuthUserService;
import com.programmers.lime.domains.member.api.dto.response.MemberLoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AuthController {

	private final OAuthUserService oauthUserService;

	@GetMapping("/auth/kakao/callback")
	public ResponseEntity<MemberLoginResponse> loginKakao(
		@RequestParam final String code
	) {
		return ResponseEntity.ok(oauthUserService.login(code));
	}
}
