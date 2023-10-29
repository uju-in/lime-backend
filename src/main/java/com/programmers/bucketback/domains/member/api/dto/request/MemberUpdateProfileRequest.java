package com.programmers.bucketback.domains.member.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record MemberUpdateProfileRequest(
	@NotNull
	String nickname,

	@NotNull
	String introduction
) {
}
