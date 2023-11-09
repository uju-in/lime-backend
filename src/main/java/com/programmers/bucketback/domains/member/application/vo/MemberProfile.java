package com.programmers.bucketback.domains.member.application.vo;

import com.programmers.bucketback.domains.member.domain.Member;

public record MemberProfile(
	MemberInfo memberInfo,
	String introduction
) {
	public static MemberProfile from(final Member member) {
		MemberInfo memberInfo = MemberInfo.builder()
			.memberId(member.getId())
			.nickName(member.getNickname())
			.level(member.getLevelPoint())
			.build();

		return new MemberProfile(memberInfo, member.getIntroduction());
	}
}
