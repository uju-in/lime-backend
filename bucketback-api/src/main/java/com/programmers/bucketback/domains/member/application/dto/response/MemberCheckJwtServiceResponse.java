package com.programmers.bucketback.domains.member.application.dto.response;

public record MemberCheckJwtServiceResponse(
	Long memberId,
	String nickname
) {
}
