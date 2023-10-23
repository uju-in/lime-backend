package com.programmers.bucketback.domains.member.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.domains.member.api.dto.request.MemberRequest;
import com.programmers.bucketback.domains.member.api.dto.request.RegisterRequest;
import com.programmers.bucketback.domains.member.api.dto.response.MemberResponse;
import com.programmers.bucketback.domains.member.application.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/register")
	public ResponseEntity<MemberResponse> register(@RequestBody RegisterRequest request) {
		return ResponseEntity.ok(memberService.register(request));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<MemberResponse> authenticate(@RequestBody MemberRequest request) {
		return ResponseEntity.ok(memberService.authenticate(request));
	}
}
