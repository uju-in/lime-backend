package com.programmers.bucketback.domains.member.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record MemberUpdatePasswordRequest(
	@NotNull
	String password
) {
}
