package com.programmers.bucketback.domains.member.api;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.programmers.bucketback.domains.member.api.dto.request.MemberCheckEmailRequest;
import com.programmers.bucketback.domains.member.api.dto.request.MemberCheckNicknameRequest;
import com.programmers.bucketback.domains.member.api.dto.request.MemberLoginRequest;
import com.programmers.bucketback.domains.member.api.dto.request.MemberSignupRequest;
import com.programmers.bucketback.domains.member.api.dto.request.MemberUpdatePasswordRequest;
import com.programmers.bucketback.domains.member.api.dto.request.MemberUpdateProfileRequest;
import com.programmers.bucketback.domains.member.api.dto.response.MemberCheckEmailResponse;
import com.programmers.bucketback.domains.member.api.dto.response.MemberCheckJwtResponse;
import com.programmers.bucketback.domains.member.api.dto.response.MemberCheckNicknameResponse;
import com.programmers.bucketback.domains.member.api.dto.response.MemberGetMyPageResponse;
import com.programmers.bucketback.domains.member.api.dto.response.MemberLoginResponse;
import com.programmers.bucketback.domains.member.application.MemberService;
import com.programmers.bucketback.domains.member.application.dto.response.MemberCheckJwtServiceResponse;
import com.programmers.bucketback.domains.member.application.dto.response.MemberLoginServiceResponse;
import com.programmers.bucketback.domains.member.model.MyPage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "members", description = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

	private final MemberService memberService;

	@Operation(summary = "JWT 토큰 유효성 체크")
	@GetMapping("/check/jwt")
	public ResponseEntity<MemberCheckJwtResponse> checkJwtToken() {
		final MemberCheckJwtServiceResponse serviceResponse = memberService.checkJwtToken();
		final MemberCheckJwtResponse response = MemberCheckJwtResponse.from(serviceResponse);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "회원가입", description = "MemberSignupRequest 을 이용하여 회원가입을 합니다.")
	@PostMapping("/signup")
	public ResponseEntity<Void> signup(@Valid @RequestBody final MemberSignupRequest request) {
		memberService.signup(request.toLoginInfo(), request.nickname());

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "로그인", description = "MemberLoginRequest 을 이용하여 로그인을 합니다.")
	@PostMapping("/login")
	public ResponseEntity<MemberLoginResponse> login(@Valid @RequestBody final MemberLoginRequest request) {
		final MemberLoginServiceResponse serviceResponse = memberService.login(request.toLoginInfo());
		final MemberLoginResponse response = MemberLoginResponse.from(serviceResponse);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "회원 탈퇴")
	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteMember() {
		memberService.deleteMember();

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "프로필 수정", description = "MemberUpdateProfileRequest 을 이용하여 프로필을 수정합니다.")
	@PutMapping("/profile")
	public ResponseEntity<Void> updateProfile(@Valid @RequestBody final MemberUpdateProfileRequest request) {
		memberService.updateProfile(request.nickname(), request.introduction());

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "비밀번호 변경", description = "MemberUpdatePasswordRequest 을 이용하여 비밀번호를 변경합니다.")
	@PutMapping("/password")
	public ResponseEntity<Void> updatePassword(@Valid @RequestBody final MemberUpdatePasswordRequest request) {
		memberService.updatePassword(request.password());

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "닉네임 중복 확인", description = "MemberCheckNicknameRequest 을 이용하여 닉네임 중복 확인을 합니다.")
	@PostMapping("/check/nickname")
	public ResponseEntity<MemberCheckNicknameResponse> checkNickname(
		@Valid @RequestBody final MemberCheckNicknameRequest request
	) {
		memberService.checkNickname(request.nickname());

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "이메일 인증", description = "MemberCheckEmailRequest 을 이용하여 이메일 중복 확인을 한 후 이메일 인증번호를 보냅니다.")
	@PostMapping("/check/email")
	public ResponseEntity<MemberCheckEmailResponse> checkEmail(
		@Valid @RequestBody final MemberCheckEmailRequest request
	) {
		final String code = memberService.checkEmail(request.email());
		final MemberCheckEmailResponse response = new MemberCheckEmailResponse(code);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "마이페이지 조회", description = "닉네임을 이용하여 마이페이지를 조회합니다.")
	@GetMapping("/{nickname}")
	public ResponseEntity<MemberGetMyPageResponse> getMyPage(
		@PathVariable final String nickname,
		final HttpServletResponse servletResponse
	) {
		MyPage myPage = memberService.getMyPage(nickname);
		MemberGetMyPageResponse response = MemberGetMyPageResponse.from(myPage);
		servletResponse.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, max-age=0, must-revalidate");
		servletResponse.setHeader("Pragma", "no-cache");

		return ResponseEntity.ok(response);
	}

	@PutMapping("/profile/image")
	@ResponseBody
	public ResponseEntity<Void> upload(
		@RequestParam(name = "image", required = false) final MultipartFile multipartFile
	) throws IOException {
		memberService.updateProfileImage(multipartFile);

		return ResponseEntity.ok().build();
	}
}
