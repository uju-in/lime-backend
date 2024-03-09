package com.programmers.lime.domains.auth.application.dto;

import com.programmers.lime.domains.member.domain.Member;

public record MemberLoginServiceResponse(
        Long memberId,
		String nickname,
		String accessToken,
		String refreshToken
) {
	public static MemberLoginServiceResponse from(
		final Member member,
		final String accessToken,
		final String refreshToken
	) {
		return new MemberLoginServiceResponse(
			member.getId(),
			member.getNickname(),
			accessToken,
			refreshToken
		);
	}
}
