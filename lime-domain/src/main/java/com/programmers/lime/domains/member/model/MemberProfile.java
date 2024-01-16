package com.programmers.lime.domains.member.model;

import com.programmers.lime.domains.member.domain.Member;

import lombok.Builder;

@Builder
public record MemberProfile(
	Long memberId,
	String nickname,
	String profileImage,
	int level,
	String hobby,
	long career,
	String content,
	String mbti
) {
	public static MemberProfile from(final Member member) {
		return MemberProfile.builder()
			.memberId(member.getId())
			.nickname(member.getNickname())
			.profileImage(member.getProfileImage())
			.level(member.getLevel())
			.hobby(member.getHobby())
			.career(member.getCareer())
			.content(member.getContent())
			.mbti(member.getMbti())
			.build();
	}
}
