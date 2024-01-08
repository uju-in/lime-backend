package com.programmers.lime.domains.member.model;

import com.programmers.lime.domains.member.domain.Member;

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
			.profileImage(member.getProfileImage())
			.level(member.getLevel())
			.introduction(member.getIntroduction())
			.build();
	}
}
