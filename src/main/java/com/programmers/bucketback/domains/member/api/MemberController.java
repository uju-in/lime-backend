package com.programmers.bucketback.domains.member.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.domains.member.api.dto.request.MemberCheckEmailRequest;
import com.programmers.bucketback.domains.member.api.dto.request.MemberCheckNicknameRequest;
import com.programmers.bucketback.domains.member.api.dto.request.MemberLoginRequest;
import com.programmers.bucketback.domains.member.api.dto.request.MemberSignupRequest;
import com.programmers.bucketback.domains.member.api.dto.request.MemberUpdatePasswordRequest;
import com.programmers.bucketback.domains.member.api.dto.request.MemberUpdateProfileRequest;
import com.programmers.bucketback.domains.member.api.dto.response.MemberCheckEmailResponse;
import com.programmers.bucketback.domains.member.api.dto.response.MemberCheckNicknameResponse;
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
		memberService.signup(request.toLoginInfo(), request.nickname());

		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
	public ResponseEntity<MemberLoginResponse> login(@Valid @RequestBody final MemberLoginRequest request) {
		final LoginMemberServiceResponse response = memberService.login(request.toLoginInfo());

		return ResponseEntity.ok(response.toMemberLoginResponse());
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteMember() {
		memberService.deleteMember();

		return ResponseEntity.ok().build();
	}

	@PutMapping("/profile")
	public ResponseEntity<Void> updateProfile(@Valid @RequestBody final MemberUpdateProfileRequest request) {
		memberService.updateProfile(request.nickname(), request.introduction());

		return ResponseEntity.ok().build();
	}

	@PutMapping("/password")
	public ResponseEntity<Void> updatePassword(@Valid @RequestBody final MemberUpdatePasswordRequest request) {
		memberService.updatePassword(request.password());

		return ResponseEntity.ok().build();
	}

	@PostMapping("/check/nickname")
	public ResponseEntity<MemberCheckNicknameResponse> checkNickname(
		@Valid @RequestBody final MemberCheckNicknameRequest request
	) {
		final boolean isDuplicated = memberService.checkNickname(request.nickname());
		final MemberCheckNicknameResponse response = new MemberCheckNicknameResponse(isDuplicated);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/check/email")
	public ResponseEntity<MemberCheckEmailResponse> checkEmail(
		@Valid @RequestBody final MemberCheckEmailRequest request
	) {
		final String code = memberService.checkEmail(request.email());
		final MemberCheckEmailResponse response = new MemberCheckEmailResponse(code);

		return ResponseEntity.ok(response);
	}
}
