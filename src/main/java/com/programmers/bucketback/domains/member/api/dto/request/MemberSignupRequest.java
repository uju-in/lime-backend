package com.programmers.bucketback.domains.member.api.dto.request;

import com.programmers.bucketback.domains.member.application.dto.request.SignupMemberServiceRequest;

import jakarta.validation.constraints.NotNull;

public record MemberSignupRequest(
	@NotNull
	String email,

	@NotNull
	String password,

	@NotNull
	String nickname
) {
	public SignupMemberServiceRequest toSignupMemberServiceRequest() {
		return new SignupMemberServiceRequest(email, password, nickname);
	}
}
