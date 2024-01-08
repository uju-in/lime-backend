package com.programmers.lime.domains.member.application.dto.response;

public record MemberLoginServiceResponse(
	Long memberId,
	String nickname,
	String accessToken,
	String refreshToken
) {
}
