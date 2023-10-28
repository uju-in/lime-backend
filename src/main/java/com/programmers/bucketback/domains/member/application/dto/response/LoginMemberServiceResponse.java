package com.programmers.bucketback.domains.member.application.dto.response;

import com.programmers.bucketback.domains.member.api.dto.response.MemberLoginResponse;

public record LoginMemberServiceResponse(
	Long memberId,
	String jwtToken
) {
	public MemberLoginResponse toMemberLoginResponse() {
		return new MemberLoginResponse(memberId, jwtToken);
	}
}
