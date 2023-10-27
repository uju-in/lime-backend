package com.programmers.bucketback.domains.member.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.domains.member.api.dto.request.MemberLoginRequest;
import com.programmers.bucketback.domains.member.api.dto.request.MemberSignupRequest;
import com.programmers.bucketback.domains.member.api.dto.response.MemberLoginResponse;
import com.programmers.bucketback.domains.member.application.MemberService;
import com.programmers.bucketback.domains.member.application.dto.response.LoginMemberServiceResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/signup")
	public ResponseEntity<Void> signup(@Valid @RequestBody final MemberSignupRequest request) {
		memberService.signup(request.toSignupMemberServiceRequest());

		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
	public ResponseEntity<MemberLoginResponse> login(@Valid @RequestBody final MemberLoginRequest request) {
		LoginMemberServiceResponse response = memberService.login(request.toLoginMemberServiceRequest());

		return ResponseEntity.ok(response.toMemberLoginResponse());
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteMember() {
		memberService.deleteMember();

		return ResponseEntity.ok().build();
	}
}
