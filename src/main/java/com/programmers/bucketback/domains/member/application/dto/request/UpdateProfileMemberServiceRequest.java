package com.programmers.bucketback.domains.member.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateProfileMemberServiceRequest(
	@NotNull
	String nickname,

	@NotNull
	String introduction
) {
}
