package com.programmers.bucketback.domains.member.model;

import com.programmers.bucketback.domains.member.domain.Level;
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
		final String profileImage,
		final int level
	) {
		this.memberId = memberId;
		this.nickName = nickName;
		this.profileImage = profileImage;
		this.level =  Level.from(level);
	}

	public static MemberInfo from(final Member member) {
		return MemberInfo.builder()
			.memberId(member.getId())
			.nickName(member.getNickname())
			.profileImage(member.getProfileImage())
			.level(member.getLevel())
			.build();
	}
}
