package com.programmers.bucketback.domains.member.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record SignupMemberServiceRequest(
	@NotNull
	String email,

	@NotNull
	String password,

	@NotNull
	String nickname
) {
}
