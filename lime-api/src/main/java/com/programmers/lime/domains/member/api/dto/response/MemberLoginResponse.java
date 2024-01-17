package com.programmers.lime.domains.member.api.dto.response;

import com.programmers.lime.domains.member.domain.Member;

public record MemberLoginResponse(
        Long memberId,
		String nickname,
		String accessToken
) {
	public static MemberLoginResponse from(
		final Member member,
		final String accessToken
	) {
		return new MemberLoginResponse(
			member.getId(),
			member.getNickname(),
			accessToken
		);
	}
}
