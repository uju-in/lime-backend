package com.programmers.bucketback.domains.member.model;

import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.domain.vo.Introduction;

import lombok.Builder;

@Builder
public record MemberProfile(
	Long memberId,
	String nickname,
	String profileImage,
	Integer levelPoint,
	Introduction introduction
) {
	public static MemberProfile from(final Member member) {
		return MemberProfile.builder()
			.memberId(member.getId())
			.nickname(member.getNickname())
			.profileImage(member.getProfileImage())
			.levelPoint(member.getLevelPoint())
			.introduction(member.getIntroduction())
			.build();
	}
}
