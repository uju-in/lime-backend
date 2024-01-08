package com.programmers.lime.domains.member.application.dto.response;

public record MemberCheckJwtServiceResponse(
	Long memberId,
	String nickname
) {
}
