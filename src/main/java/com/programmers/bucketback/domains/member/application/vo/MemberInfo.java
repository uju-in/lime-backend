package com.programmers.bucketback.domains.member.application.vo;

import lombok.Builder;

@Builder
public record MemberInfo(
	Long memberId,
	String nickName,
	String profileImage,
	int level
) {
	public MemberInfo(
		final Long memberId,
		final String nickName
	) {
		this(memberId, nickName, null, 0);
	}
}
