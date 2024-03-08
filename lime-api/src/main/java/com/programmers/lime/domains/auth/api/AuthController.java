package com.programmers.lime.domains.auth.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.lime.domains.auth.application.OAuthUserService;
import com.programmers.lime.domains.member.api.dto.response.MemberLoginResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {

	private final OAuthUserService oauthUserService;

	@GetMapping("/auth/kakao/callback")
	public ResponseEntity<MemberLoginResponse> loginKakao(
		@RequestParam final String code
	) {
		return ResponseEntity.ok(oauthUserService.login(code));
	}

	@GetMapping("/join")
	public ResponseEntity<String> join(){
		return ResponseEntity.ok("join");
	}
}
