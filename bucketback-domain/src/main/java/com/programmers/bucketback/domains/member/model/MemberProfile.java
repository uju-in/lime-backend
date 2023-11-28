package com.programmers.bucketback.domains.member.model;

import com.programmers.bucketback.domains.member.domain.Member;

import lombok.Builder;

@Builder
public record MemberProfile(
	Long memberId,
	String nickname,
	String profileImage,
	int level,
	String introduction
) {
	public static MemberProfile from(final Member member) {
		return MemberProfile.builder()
			.memberId(member.getId())
			.nickname(member.getNickname())
			.profileImage(member.getProfileImageUrl())
			.level(member.getLevel())
			.introduction(member.getIntroduction())
			.build();
	}
}
