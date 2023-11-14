package com.programmers.bucketback.domains.member.application.dto.response;

public record LoginMemberServiceResponse(
	Long memberId,
	String nickname,
	String jwtToken
) {
}
