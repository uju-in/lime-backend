package com.programmers.bucketback.domains.member.model;

import com.programmers.bucketback.domains.member.domain.Member;
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
		final String nickName,
		final int level
	) {
		this(memberId, nickName, null, level);
	}

	public static MemberInfo from(final Member member) {
		return new MemberInfo(member.getId(), member.getNickname(), member.getLevel());
	}
}
