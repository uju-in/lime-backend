package com.programmers.lime.domains.auth.api.dto;

import com.programmers.lime.domains.auth.application.dto.MemberLoginServiceResponse;

public record MemberLoginResponse(
		Long memberId,
		String nickname,
		String accessToken
) {
	public static MemberLoginResponse from(final MemberLoginServiceResponse response) {
		return new MemberLoginResponse(response.memberId(), response.nickname(), response.accessToken());
	}
}
