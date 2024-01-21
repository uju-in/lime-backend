package com.programmers.lime.domains.member.model;

import com.programmers.lime.domains.member.domain.Level;
import com.programmers.lime.domains.member.domain.Member;

import lombok.Builder;

@Builder
public record MemberInfo(
	Long memberId,
	String nickname,
	String profileImage,
	int level
) {

	public MemberInfo(
		final Long memberId,
		final String nickname,
		final String profileImage,
		final int level
	) {
		this.memberId = memberId;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.level = Level.from(level);
	}

	public static MemberInfo from(final Member member) {
		return new MemberInfo(
			member.getId(),
			member.getNickname(),
			member.getProfileImage(),
			member.getLevelPoint()
		);
	}
}
