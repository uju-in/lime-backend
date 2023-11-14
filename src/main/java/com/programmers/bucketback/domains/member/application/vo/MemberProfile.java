package com.programmers.bucketback.domains.member.application.vo;

import com.programmers.bucketback.domains.member.domain.Member;

import lombok.Builder;

@Builder
public record MemberProfile(
	Long memberId,
	String nickname,
	// String profileImage, //refactor : 도메인 필드 추가시 반영
	Integer levelPoint,
	String introduction
) {
	public static MemberProfile from(final Member member) {
		return MemberProfile.builder()
			.memberId(member.getId())
			.nickname(member.getNickname())
			// .profileImage(member.getProfileImage())
			.levelPoint(member.getLevelPoint())
			.introduction(member.getIntroduction())
			.build();
	}
}
