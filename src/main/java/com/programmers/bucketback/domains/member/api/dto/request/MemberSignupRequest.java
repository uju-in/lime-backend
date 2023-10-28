package com.programmers.bucketback.domains.member.api.dto.request;

import com.programmers.bucketback.domains.member.domain.LoginInfo;

import jakarta.validation.constraints.NotNull;

public record MemberSignupRequest(
	@NotNull
	String email,

	@NotNull
	String password,

	@NotNull
	String nickname
) {
	public LoginInfo toLoginInfo() {
		return new LoginInfo(email, password);
	}
}
