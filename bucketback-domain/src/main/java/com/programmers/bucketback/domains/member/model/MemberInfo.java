package com.programmers.bucketback.domains.member.model;

import com.programmers.bucketback.domains.member.domain.Level;
import com.programmers.bucketback.domains.member.domain.Member;

public record MemberInfo(
	Long memberId,
	String nickName,
	String profileImage,
	int level
) {

	public MemberInfo(
		final Long memberId,
		final String nickName,
		final String profileImage,
		final int level
	) {
		this.memberId = memberId;
		this.nickName = nickName;
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
