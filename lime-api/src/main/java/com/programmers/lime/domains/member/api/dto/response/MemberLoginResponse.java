package com.programmers.lime.domains.member.api.dto.response;

import com.programmers.lime.domains.member.domain.Member;

public record MemberLoginResponse(
        Long memberId,
		String nickname,
		String accessToken,
		String refreshToken
) {
	public static MemberLoginResponse from(
		final Member member,
		final String accessToken,
		final String refreshToken
	) {
		return new MemberLoginResponse(
			member.getId(),
			member.getNickname(),
			accessToken,
			refreshToken
		);
	}
}
