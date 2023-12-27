package com.programmers.bucketback.domains.member.api.dto.response;

import com.programmers.bucketback.domains.member.application.dto.response.MemberLoginServiceResponse;

public record MemberLoginResponse(
        Long memberId,
		String nickname,
		String accessToken,
		String refreshToken
) {
	public static MemberLoginResponse from(final MemberLoginServiceResponse serviceResponse) {
		return new MemberLoginResponse(
			serviceResponse.memberId(),
			serviceResponse.nickname(),
			serviceResponse.accessToken(),
			serviceResponse.refreshToken()
		);
	}
}
