package com.programmers.lime.domains.member.api.dto.response;

import com.programmers.lime.domains.member.application.dto.response.MemberLoginServiceResponse;

public record MemberLoginResponse(
        Long memberId,
		String nickname,
		String accessToken
) {
	public static MemberLoginResponse from(final MemberLoginServiceResponse serviceResponse) {
		return new MemberLoginResponse(
			serviceResponse.memberId(),
			serviceResponse.nickname(),
			serviceResponse.accessToken()
		);
	}
}
