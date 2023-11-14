package com.programmers.bucketback.domains.member.api.dto.response;

import com.programmers.bucketback.domains.member.application.dto.response.LoginMemberServiceResponse;

public record MemberLoginResponse(
        Long memberId,
		String nickname,
        String jwtToken
) {
	public static MemberLoginResponse from(final LoginMemberServiceResponse serviceResponse) {
		return new MemberLoginResponse(
			serviceResponse.memberId(),
			serviceResponse.nickname(),
			serviceResponse.jwtToken()
		);
	}
}
