package com.programmers.bucketback.domains.member.api.dto.response;

public record MemberLoginResponse(
        Long memberId,
		String nickname,
        String jwtToken
) {
}
